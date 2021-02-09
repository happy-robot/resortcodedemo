package kz.kaps.resort.entrypoints.html.landlord;

import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreateAdChooseHousingTypeEndPoint {

    @GetMapping("/my/ads/create/choose-housing-type")
    public String createAdChooseHousingType(Device device, Authentication authentication, Model model){
        if(authentication == null) return "redirect:/error/403";
        String viewName = "landlord/createAdChooseHousingType";
        if (device.isMobile()) {
            viewName = "landlord/mobile/createAdChooseHousingType";
        }

        return viewName;
    }
}
