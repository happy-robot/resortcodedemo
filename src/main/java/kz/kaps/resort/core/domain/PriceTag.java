package kz.kaps.resort.core.domain;

import lombok.*;

import java.time.LocalDate;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceTag {

    private Long id;
    private int price;
    private LocalDate startDate;
    private LocalDate endDate;

}
