package kz.kaps.resort.entrypoints.html.landlord;

import kz.kaps.resort.core.usecase.ad.landlord.AdsCountsByStatusDto;
import kz.kaps.resort.core.usecase.ad.landlord.GetAdsCountsByStatusUseCase;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CabinetEndPoint {

    private GetAdsCountsByStatusUseCase getAdsCountsByStatusUseCase;

    @Autowired
    public CabinetEndPoint(GetAdsCountsByStatusUseCase getAdsCountsByStatusUseCase) {
        this.getAdsCountsByStatusUseCase = getAdsCountsByStatusUseCase;
    }

    @GetMapping("/cabinet")
    public String adList(Device device, Authentication authentication, Model model) {
        if (authentication == null) return "redirect:/error/403";
        String username = authentication.getName();
        if (StringUtils.isEmpty(username)) return "redirect:/error/403";

        if (!device.isMobile()) {
            return "redirect:/my/ads";
        }

        try {
            AdsCountsByStatusDto countsByStatusDto = getAdsCountsByStatusUseCase.getAdsCountsByStatusUseCase(username);
            model.addAttribute("adsCountsByStatus", countsByStatusDto);
        } catch (ForbiddenException e) {
            e.printStackTrace();
            return "redirect:/error/403";
        }
        return "landlord/mobile/cabinet";
    }

}
