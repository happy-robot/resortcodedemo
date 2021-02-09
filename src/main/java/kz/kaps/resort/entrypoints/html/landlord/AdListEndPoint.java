package kz.kaps.resort.entrypoints.html.landlord;

import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.usecase.ad.landlord.getlandlordads.GetLandlordAdsUseCase;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import kz.kaps.resort.entrypoints.html.landlord.dto.AdShortDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdListEndPoint {

    private GetLandlordAdsUseCase getLandlordAdsUseCase;
    private ModelMapper modelMapper;

    @Autowired
    public AdListEndPoint(GetLandlordAdsUseCase getLandlordAdsUseCase,
                          ModelMapper modelMapper){
        this.getLandlordAdsUseCase = getLandlordAdsUseCase;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/my/ads")
    public String adList(@RequestParam(value = "status", required = false) AdStatusEnum status, Device device,
                         Authentication authentication, Model model) {
        if (authentication == null) return "redirect:/error/403";
        String username = authentication.getName();
        if (StringUtils.isEmpty(username)) return "redirect:/error/403";

        String viewName = "landlord/adList";
        if (device.isMobile()) {
            viewName = "landlord/mobile/adList";
        }
        List<kz.kaps.resort.core.usecase.ad.landlord.getlandlordads.AdShortDto> ads = null;
        try {
            ads = getLandlordAdsUseCase.getAdsByOwnerAndStatus(username, status);
        } catch (ForbiddenException e) {
            e.printStackTrace();
            return "redirect:/error/403";
        }
        List<AdShortDto> adDtos = ads != null ? ads.stream().map(nxtAd -> modelMapper.map(nxtAd, AdShortDto.class))
                .collect(Collectors.toList()) : Collections.EMPTY_LIST;

        model.addAttribute("ads", adDtos);
        model.addAttribute("status", status);
        return viewName;
    }

}
