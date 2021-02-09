package kz.kaps.resort.entrypoints.html;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageDto {

    private Long id;

    private String fileNameWithExt;

    private String mimeType;

    private Integer orderNumber;

    private String urlForExtraSmallSize;

    private String urlForSmallSize;

    private String urlForMediumSize;

    private String urlForBigSize;

    private String urlForFullSize;

    private String urlForOriginalSize;

}
