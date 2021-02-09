package kz.kaps.resort.entrypoints.html;

import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.domain.HousingType;
import kz.kaps.resort.entrypoints.html.landlord.dto.LocalityDto;
import lombok.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdDto {

    private Long id;
    private UserDto owner;

    private List<ImageDto> images;

    private Boolean isPricePerPerson;
    private Integer weekdayPricePerDay;
    private Integer holidayPricePerDay;

    private Integer roomNum;
    private Integer floor;
    private Integer floorNum;
    private Integer sleepNum;
    private Integer area;
    private Boolean hotWater;
    private Boolean coldWater;
    private Boolean tv;
    private Boolean fridge;
    private Boolean stove;
    private Boolean washer;
    private Boolean microwave;
    private Boolean wifi;
    private Boolean conditioner;
    private Boolean brazier;
    private Boolean kazan;//казан

    private String header;
    private String description;
    private String shortDescription;

    private Date createdAt;
    private Date editedAt;

    private HousingType housingType;

    private Integer summerHousesCount;

    private Integer viewsNum;

    private Boolean sauna;
    private Boolean bathhouse;
    private Boolean restaurant;
    private Boolean swimmingPool;
    private Boolean billiards;
    private Boolean summerKitchen;//летняя кухня
    private Boolean cook;//повар
    private Boolean alcove;//беседка
    private Integer territoryArea;//площадь территории в сотках
    private Boolean tapchan;//тапчан
    private Boolean bicycles;//велосипеды
    private Boolean quadBikes;//квадроциклы
    private Boolean playground;//детская площадка

    private Double latitude;
    private Double longitude;

    private AdStatusEnum status;

    private LocalityDto locality;
    private String street;
    private String houseNumber;

    public ImageDto getFirstImage(){
        if(images != null && !images.isEmpty()) {
            Optional<ImageDto> image = images.stream().filter(im -> im.getOrderNumber() == 1).findFirst();
            if(image.isPresent()) return image.get();
        }
        return null;
    }

    public List<ImageDto> getImagesExceptFirst(){
        if(images != null && !images.isEmpty()) {
            return images.stream().filter(im -> im.getOrderNumber() != 1).collect(Collectors.toList());
        } else return Collections.emptyList();
    }

}
