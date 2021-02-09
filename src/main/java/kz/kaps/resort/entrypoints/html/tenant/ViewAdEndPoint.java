package kz.kaps.resort.entrypoints.html.tenant;

import kz.kaps.resort.core.usecase.ad.tenant.AdView;
import kz.kaps.resort.core.usecase.ad.tenant.ViewAdUseCase;
import kz.kaps.resort.entrypoints.html.tenant.dto.AdViewDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ViewAdEndPoint {

    private ViewAdUseCase viewAdUseCase;
    private ModelMapper modelMapper;

    @Autowired
    public ViewAdEndPoint(ViewAdUseCase viewAdUseCase, ModelMapper modelMapper) {
        this.viewAdUseCase = viewAdUseCase;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/ads/{id}")
    public String viewAd(@PathVariable String id, Device device, Authentication authentication, Model model) {
        Long adIdL = Long.valueOf(id);

        AdView adView = viewAdUseCase.viewAd(adIdL);
        AdViewDto adViewDto = modelMapper.map(adView, AdViewDto.class);
        if(adViewDto.getAd() == null) {
            return "redirect:/error/404";
        }

        if (device.isMobile()) model.addAttribute("isMobile", true);
        else model.addAttribute("isMobile", false);
        model.addAttribute("ad", adViewDto);
        return "tenant/viewAd";
    }

}
