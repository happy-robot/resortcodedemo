package kz.kaps.resort.entrypoints.html.landlord;

import kz.kaps.resort.core.dictionaries.GetAllCountries;
import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.HousingType;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.entrypoints.html.landlord.dto.AdFormDto;
import kz.kaps.resort.core.usecase.ad.landlord.FinishAdCreationUseCase;
import kz.kaps.resort.core.usecase.ad.landlord.StartAdCreationUseCase;
import kz.kaps.resort.core.usecase.exception.*;
import kz.kaps.resort.core.usecase.user.GetUserByUsername;
import kz.kaps.resort.entrypoints.html.landlord.dto.CountryDto;
import kz.kaps.resort.entrypoints.rest.dictionaries.CountriesRestEndPoint;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static kz.kaps.resort.entrypoints.html.utils.Utils.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
public class CreateAdEndPoint {

    private static final String COTTAGE_VIEW_NAME = "landlord/createUpdateAdCottage";
    private static final String FLAT_VIEW_NAME = "landlord/createUpdateAdFlat";
    private static final String HOUSE_VIEW_NAME = "landlord/createUpdateAdHouse";
    private static final String SUMMER_HOUSE_VIEW_NAME = "landlord/createUpdateAdSummerHouses";

    private GetUserByUsername getUserByUsername;
    private StartAdCreationUseCase startAdCreationUseCase;
    private GetAllCountries getAllCountries;
    private ModelMapper modelMapper;
    private FinishAdCreationUseCase finishAdCreationUseCase;
    private MessageSource messageSource;

    @Autowired
    public CreateAdEndPoint(
            GetUserByUsername getUserByUsername,
            StartAdCreationUseCase startAdCreationUseCase,
            GetAllCountries getAllCountries, ModelMapper modelMapper,
            FinishAdCreationUseCase finishAdCreationUseCase,
            MessageSource messageSource) {

        this.getUserByUsername = getUserByUsername;
        this.startAdCreationUseCase = startAdCreationUseCase;
        this.getAllCountries = getAllCountries;
        this.modelMapper = modelMapper;
        this.finishAdCreationUseCase = finishAdCreationUseCase;
        this.messageSource = messageSource;
    }

    @GetMapping("/my/ads/create")
    public String createAd(@RequestParam(value = "ht") HousingType housingType, Authentication authentication, Model model) {
        if(authentication == null) return "redirect:/error/403";
        String username = authentication.getName();
        if(StringUtils.isEmpty(username)) return "redirect:/error/403";
        User user = getUserByUsername.getUserByUsername(username);
        if(user == null) return "redirect:/error/403";

        Ad ad = startAdCreationUseCase.startAdCreation(housingType, user);
        AdFormDto formDto = modelMapper.map(ad, AdFormDto.class);

        model.addAttribute("newAd", formDto);
        fillModelWithDefaultFields(housingType, model, username, formDto);

        return getViewName(housingType);
    }

    private String getViewName(HousingType housingType) {
        String viewName = null;
        switch (housingType) {
            case SUMMER_HOUSE:
                viewName = SUMMER_HOUSE_VIEW_NAME;
                break;
            case COTTAGE:
                viewName = COTTAGE_VIEW_NAME;
                break;
            case FLAT:
                viewName = FLAT_VIEW_NAME;
                break;
            case HOUSE:
                viewName = HOUSE_VIEW_NAME;
                break;
            default:
                viewName = null;
        }
        return viewName;
    }

    @PostMapping("/my/ads/create")
    public String createAdProcess(@RequestParam(value = "ht") HousingType housingType, @ModelAttribute("newAd") @Valid AdFormDto newAdFlatDto, Errors errors,
                                  Authentication authentication, Model model) {
        if(authentication == null) return "redirect:/error/403";
        String ownerUsername = authentication.getName();
        if(StringUtils.isEmpty(ownerUsername)) return "redirect:/error/403";

        if(errors.hasErrors()) {
            fillModelWithDefaultFields(housingType, model, ownerUsername, newAdFlatDto);
            return getViewName(housingType);
        }

        try {
            Ad ad = modelMapper.map(newAdFlatDto, Ad.class);
            finishAdCreationUseCase.finishAdCreationUseCase(ad, ownerUsername);
        } catch (ForbiddenException e) {
            return "redirect:/error/403";
        } catch (UnexpectedException e) {
            return "redirect:/error/500";
        } catch (AdAlreadyExistsException e) {
            e.printStackTrace();
            return "redirect:/error/400";
        } catch (ImagesDoNotExistException e) {
            e.printStackTrace();
            String imagesDoNotExistMessage = messageSource.getMessage("cabinet.ad.create.form.images.err.required", null, Locale.US);
            model.addAttribute("imagesDoNotExistMessage", imagesDoNotExistMessage);
            fillModelWithDefaultFields(housingType, model, ownerUsername, newAdFlatDto);
            return getViewName(housingType);
        }

        return "redirect:/my/ads/edit/" + newAdFlatDto.getId();
    }

    private void fillModelWithDefaultFields(HousingType housingType, Model model, String ownerUsername, AdFormDto newAdFlatDto){
        List<CountryDto> countries = new ArrayList<>();
        getAllCountries.getAllCountries().forEach(c -> countries.add(modelMapper.map(c, CountryDto.class)));
        model.addAttribute("phonenumber", ownerUsername);
        model.addAttribute("countries", countries);
        Optional<CountryDto> selectedCountry = countries.stream().filter(c -> c.getId().equals(newAdFlatDto.getCountryId())).findFirst();
        if(selectedCountry.isPresent())
            model.addAttribute("localities", selectedCountry.get().getLocalities());
        else
            model.addAttribute("localities", !countries.isEmpty() ? countries.get(0).getLocalities() : new ArrayList<>(1));
        model.addAttribute("formActionUrl", "/my/ads/create?ht=" + housingType.toString());
        model.addAttribute("imageUploadURL", getUploadImageMethodURL(newAdFlatDto.getId().toString()).getPath());
        model.addAttribute("imageDeleteURL", getDeleteImageMethodURL(newAdFlatDto.getId().toString()).getPath());
        model.addAttribute("imageListURL", getImageListMethodURL(newAdFlatDto.getId().toString()).getPath());

        model.addAttribute("countryDictURL", ControllerLinkBuilder.linkTo(methodOn(CountriesRestEndPoint.class)
                .getCountries()).toUri().getPath());
    }
}
