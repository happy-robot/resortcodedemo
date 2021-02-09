package kz.kaps.resort.entrypoints.html.landlord;

import kz.kaps.resort.core.dictionaries.GetAllCountries;
import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.HousingType;
import kz.kaps.resort.entrypoints.html.landlord.dto.AdFormDto;
import kz.kaps.resort.core.usecase.ad.landlord.UpdateAdUseCase;
import kz.kaps.resort.core.usecase.ad.landlord.getadforedit.GetAdForEditUseCase;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import kz.kaps.resort.entrypoints.html.landlord.dto.CountryDto;
import kz.kaps.resort.entrypoints.rest.dictionaries.CountriesRestEndPoint;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static kz.kaps.resort.entrypoints.html.utils.Utils.*;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller
public class EditAdEndPoint {

    private static final String COTTAGE_VIEW_NAME = "landlord/createUpdateAdCottage";
    private static final String FLAT_VIEW_NAME = "landlord/createUpdateAdFlat";
    private static final String HOUSE_VIEW_NAME = "landlord/createUpdateAdHouse";
    private static final String SUMMER_HOUSE_VIEW_NAME = "landlord/createUpdateAdSummerHouses";

    private UpdateAdUseCase updateAdUseCase;
    private GetAdForEditUseCase getAdForEditUseCase;
    private GetAllCountries getAllCountries;
    private ModelMapper modelMapper;

    @Autowired
    public EditAdEndPoint(UpdateAdUseCase updateAdUseCase, GetAdForEditUseCase getAdForEditUseCase,
                                 GetAllCountries getAllCountries, ModelMapper modelMapper) {
        this.updateAdUseCase = updateAdUseCase;
        this.getAdForEditUseCase = getAdForEditUseCase;
        this.getAllCountries = getAllCountries;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/my/ads/edit/{id}")
    public String editAd(@PathVariable Long id,
                         Authentication authentication, Model model) {
        if(authentication == null) return "redirect:/error/403";

        String username = authentication.getName();
        if(StringUtils.isEmpty(username)) return "redirect:/error/403";

        Ad ad = null;
        try {
            ad = getAdForEditUseCase.getAdForEdit(id, username);
        } catch (ForbiddenException e) {
            e.printStackTrace();
            return "redirect:/error/403";
        }

        AdFormDto formDto = modelMapper.map(ad, AdFormDto.class);
        model.addAttribute("newAd", formDto);
        fillModelWithDefaultFields(model, username, formDto);

        return getViewName(ad.getHousingType());
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

    @PostMapping("/my/ads/edit/{id}")
    public String updateAdProcess(@PathVariable Long id,
                                  @ModelAttribute("newAd") @Valid AdFormDto formDto,
                                  Errors errors, Authentication authentication, Model model) {
        if(authentication == null) return "redirect:/error/403";
        if(!id.equals(formDto.getId())) return "redirect:/error/400";
        String ownerUsername = authentication.getName();
        if(StringUtils.isEmpty(ownerUsername)) return "redirect:/error/403";

        if(errors.hasErrors()) {
            fillModelWithDefaultFields(model, ownerUsername, formDto);
            return getViewName(formDto.getHousingType());
        }

        try {
            Ad ad = modelMapper.map(formDto, Ad.class);
            updateAdUseCase.updateAd(ad, ownerUsername);
        } catch (ForbiddenException e) {
            return "redirect:/error/403";
        }
        return "redirect:/my/ads/edit/" + formDto.getId();
    }

    private void fillModelWithDefaultFields(Model model, String ownerUsername, AdFormDto newAdFlatDto){
        List<CountryDto> countries = new ArrayList<>();
        getAllCountries.getAllCountries().forEach(c -> countries.add(modelMapper.map(c, CountryDto.class)));
        model.addAttribute("phonenumber", ownerUsername);
        model.addAttribute("countries", countries);
        Optional<CountryDto> selectedCountry = countries.stream().filter(c -> c.getId().equals(newAdFlatDto.getCountryId())).findFirst();
        if(selectedCountry.isPresent())
            model.addAttribute("localities", selectedCountry.get().getLocalities());
        model.addAttribute("formActionUrl", "/my/ads/edit/" + newAdFlatDto.getId());
        model.addAttribute("imageUploadURL", getUploadImageMethodURL(newAdFlatDto.getId().toString()).getPath());
        model.addAttribute("imageDeleteURL", getDeleteImageMethodURL(newAdFlatDto.getId().toString()).getPath());
        model.addAttribute("imageListURL", getImageListMethodURL(newAdFlatDto.getId().toString()).getPath());

        model.addAttribute("countryDictURL", ControllerLinkBuilder.linkTo(methodOn(CountriesRestEndPoint.class)
                .getCountries()).toUri().getPath());
    }

}
