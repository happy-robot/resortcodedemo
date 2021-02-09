package kz.kaps.resort.core.usecase.ad.landlord;

import kz.kaps.resort.core.domain.AdStatusEnum;
import kz.kaps.resort.core.usecase.exception.ForbiddenException;

import java.util.List;

import static kz.kaps.resort.core.usecase.utils.UseCaseUtils.failsIfOwnerUsernameIsEmpty;

public class GetAdsCountsByStatusUseCase {

    private GetAdsCountsByStatusAndUsername getAdsCountsByStatusAndUsername;

    public GetAdsCountsByStatusUseCase(GetAdsCountsByStatusAndUsername getAdsCountsByStatusAndUsername) {
        this.getAdsCountsByStatusAndUsername = getAdsCountsByStatusAndUsername;
    }

    public AdsCountsByStatusDto getAdsCountsByStatusUseCase(String username) throws ForbiddenException {
        failsIfOwnerUsernameIsEmpty(username);

        List<AdsCountsByStatusProjection> adsCountsByStatusProjections = getAdsCountsByStatusAndUsername.getAdsCountsByStatusAndUsername(username);
        return projectionsToDto(adsCountsByStatusProjections);
    }

    private AdsCountsByStatusDto projectionsToDto(List<AdsCountsByStatusProjection> adsCountsByStatusProjections) {
        if(adsCountsByStatusProjections == null) return AdsCountsByStatusDto.builder().build();

        long draftCount = 0;
        long moderationCount = 0;
        long rejectedCount = 0;
        long publishedCount = 0;
        long archiveCount = 0;

        for(int i = 0; i < adsCountsByStatusProjections.size(); i++) {
            AdsCountsByStatusProjection nxt = adsCountsByStatusProjections.get(i);
            if(nxt.getStatus() == AdStatusEnum.DRAFT) draftCount = nxt.getCount();
            else if(nxt.getStatus() == AdStatusEnum.MODERATION) moderationCount = nxt.getCount();
            else if(nxt.getStatus() == AdStatusEnum.REJECTED) rejectedCount = nxt.getCount();
            else if(nxt.getStatus() == AdStatusEnum.PUBLISHED) publishedCount = nxt.getCount();
            else if(nxt.getStatus() == AdStatusEnum.ARCHIVE) archiveCount = nxt.getCount();
        }

        return AdsCountsByStatusDto.builder()
                .draft(draftCount)
                .moderation(moderationCount)
                .rejected(rejectedCount)
                .published(publishedCount)
                .archive(archiveCount)
                .build();
    }
}
