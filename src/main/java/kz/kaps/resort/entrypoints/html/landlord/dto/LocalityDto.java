package kz.kaps.resort.entrypoints.html.landlord.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LocalityDto {

    private Long id;

    private String nameRu;

    private String nameEn;

    private String name;

    private String imageFileNameWithExt;

    private String imageUrl;

}
