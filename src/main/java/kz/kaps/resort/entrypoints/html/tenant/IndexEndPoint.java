package kz.kaps.resort.entrypoints.html.tenant;

import kz.kaps.resort.core.dictionaries.Locality;
import kz.kaps.resort.core.domain.AdForSearch;
import kz.kaps.resort.core.usecase.ad.tenant.GetHotAdsUseCase;
import kz.kaps.resort.core.usecase.ad.tenant.GetLocalitiesWhereAdsExistUseCase;
import kz.kaps.resort.entrypoints.html.landlord.dto.LocalityDto;
import kz.kaps.resort.entrypoints.rest.tenant.AdForSearchDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexEndPoint {

    private GetLocalitiesWhereAdsExistUseCase getLocalitiesWhereAdsExistUseCase;
    private GetHotAdsUseCase getHotAdsUseCase;
    private ModelMapper modelMapper;

    @Autowired
    public IndexEndPoint(GetLocalitiesWhereAdsExistUseCase getLocalitiesWhereAdsExistUseCase,
                         ModelMapper modelMapper, GetHotAdsUseCase getHotAdsUseCase) {
        this.getLocalitiesWhereAdsExistUseCase = getLocalitiesWhereAdsExistUseCase;
        this.getHotAdsUseCase = getHotAdsUseCase;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/")
    public String index(Device device, Model model) {
        String viewName = "tenant/index";
        if (device.isMobile()) {
            viewName = "tenant/mobile/index";
        }
        List<Locality> localities = getLocalitiesWhereAdsExistUseCase.getLocalitiesWhereAdsExist();
        List<AdForSearch> ads = getHotAdsUseCase.getHotAdsUseCase();
        List<AdForSearchDto> adsDtos = ads.stream()
                .map(l -> modelMapper.map(l, AdForSearchDto.class))
                .collect(Collectors.toList());
        List<LocalityDto> localitiesDtos = localities.stream()
                .map(l -> modelMapper.map(l, LocalityDto.class))
                .collect(Collectors.toList());
        model.addAttribute("localities", localitiesDtos);
        model.addAttribute("hotAds", adsDtos);
        model.addAttribute("selectedNavState", "index");
        return viewName;
    }
}
