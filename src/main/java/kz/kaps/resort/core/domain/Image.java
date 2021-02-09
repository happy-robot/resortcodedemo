package kz.kaps.resort.core.domain;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    private Long id;

    private String fileNameWithExt;

    private String mimeType;

    private Integer orderNumber;


    @Override
    public String toString(){
        return "[" + id + ", " + fileNameWithExt + ", " + mimeType + ", " + orderNumber + "]";
    }

}
