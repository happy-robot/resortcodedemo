package kz.kaps.resort.entrypoints.html.landlord;

import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.ad.landlord.DeactivateAdUseCase;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;
import kz.kaps.resort.core.usecase.user.GetUserByUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class DeactivateAdEndPoint {

    private GetUserByUsername getUserByUsername;
    private DeactivateAdUseCase deactivateAdUseCase;

    @Autowired
    public DeactivateAdEndPoint(DeactivateAdUseCase deactivateAdUseCase,
                              GetUserByUsername getUserByUsername){
        this.deactivateAdUseCase = deactivateAdUseCase;
        this.getUserByUsername = getUserByUsername;
    }

    @GetMapping("/my/ads/{id}/deactivate")
    public String doDeactivateAd(@PathVariable String id, Authentication authentication) {
        if(authentication == null) return "redirect:/error/403";

        if(StringUtils.isEmpty(id)) return "redirect:/error/404";
        Long adIdL = Long.valueOf(id);

        if(adIdL == null) return "redirect:/error/404";

        String username = authentication.getName();

        try {
            deactivateAdUseCase.deactivateAd(adIdL, username);
        } catch (ForbiddenException e) {
            e.printStackTrace();
            return "redirect:/error/403";
        }

        return "redirect:/my/ads";
    }
}
