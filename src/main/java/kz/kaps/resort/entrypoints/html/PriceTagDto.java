package kz.kaps.resort.entrypoints.html;

import lombok.*;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PriceTagDto {

    private Long id;
    private int price;
    private String startDate;
    private String endDate;

}
