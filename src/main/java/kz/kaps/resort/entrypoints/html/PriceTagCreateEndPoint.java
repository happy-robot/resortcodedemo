package kz.kaps.resort.entrypoints.html;

import kz.kaps.resort.core.domain.Ad;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.ad.GetAdUseCase;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import kz.kaps.resort.core.usecase.pricetag.SetPriceTagUseCase;
import kz.kaps.resort.core.usecase.user.GetUserByUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Controller
public class PriceTagCreateEndPoint {

    private GetUserByUsername getUserByUsername;
    private SetPriceTagUseCase setPriceTagUseCase;
    private GetAdUseCase getAdUseCase;


    @Autowired
    public PriceTagCreateEndPoint(GetUserByUsername getUserByUsername, GetAdUseCase getAdUseCase, SetPriceTagUseCase setPriceTagUseCase){
        this.getUserByUsername = getUserByUsername;
        this.getAdUseCase = getAdUseCase;
        this.setPriceTagUseCase = setPriceTagUseCase;
    }

    @PostMapping("/my/ads/{adId}/pricetags")
    public String setPriceTagProcess(@PathVariable String adId, @ModelAttribute("priceTag") @Valid PriceTagDto priceTagDto,
                                Errors errors, Authentication authentication){
        if(authentication == null) return "redirect:/error/403";

        String username = authentication.getName();
        if(StringUtils.isEmpty(username)) return "redirect:/error/403";

        User user = getUserByUsername.getUserByUsername(username);

        if(user == null) return "redirect:/error/403";

        if(errors.hasErrors()) return "redirect:/my/ads/" + adId + "/edit";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d.MM.yyyy");
        LocalDate startDate = LocalDate.parse(priceTagDto.getStartDate(), formatter);
        LocalDate endDate = LocalDate.parse(priceTagDto.getEndDate(), formatter);

        Long adIdL = Long.valueOf(adId);
        Ad ad = getAdUseCase.getAd(adIdL);

        try {
            setPriceTagUseCase.setPriceTagUseCase(user, ad, startDate, endDate, priceTagDto.getPrice());
        } catch (ForbiddenException e) {
            e.printStackTrace();
            return "redirect:/error/403";
        }

        return "redirect:/my/ads/" + adId + "/edit";
    }
}
