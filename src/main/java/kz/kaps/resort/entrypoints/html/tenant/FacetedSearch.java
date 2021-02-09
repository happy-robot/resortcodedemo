package kz.kaps.resort.entrypoints.html.tenant;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FacetedSearch {

    @GetMapping("/search")
    public String search(@RequestParam(value = "q", required = false) String query, Device device, Model model) {

        if (device.isMobile()) model.addAttribute("isMobile", true);
        else model.addAttribute("isMobile", false);

        return "tenant/facetedSearch";
    }

}
