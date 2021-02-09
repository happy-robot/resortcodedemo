package kz.kaps.resort.core.usecase.ad.landlord;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdsCountsByStatusDto {

    private long draft;
    private long moderation;
    private long rejected;
    private long published;
    private long archive;

}
