package kz.kaps.resort.configuration;

import kz.kaps.resort.core.communication.SMSService;
import kz.kaps.resort.core.dictionaries.GetAllLocalities;
import kz.kaps.resort.core.properties.ImageProperties;
import kz.kaps.resort.core.usecase.ad.*;
import kz.kaps.resort.core.usecase.ad.landlord.*;
import kz.kaps.resort.core.usecase.ad.landlord.getadforedit.GetAdForEditUseCase;
import kz.kaps.resort.core.usecase.ad.landlord.getlandlordads.GetLandlordAdsUseCase;
import kz.kaps.resort.core.usecase.ad.tenant.*;
import kz.kaps.resort.core.usecase.image.*;
import kz.kaps.resort.core.usecase.image.resizing.ResizeImagesService;
import kz.kaps.resort.core.usecase.pricetag.*;
import kz.kaps.resort.core.usecase.user.*;
import kz.kaps.resort.dataproviders.image.ResizeImagesServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public GetCottagesForHomePageUseCase getCottagesForHomePageUseCase(GetAdsByHousingType getAdsByHousingType){
        return new GetCottagesForHomePageUseCase(getAdsByHousingType);
    }

    @Bean
    public GetFlatsForHomePageUseCase getFlatsForHomePageUseCase(GetAdsByHousingType getAdsByHousingType){
        return new GetFlatsForHomePageUseCase(getAdsByHousingType);
    }

    @Bean
    public GetHousesForHomePageUseCase getHousesForHomePageUseCase(GetAdsByHousingType getAdsByHousingType){
        return new GetHousesForHomePageUseCase(getAdsByHousingType);
    }

    @Bean
    public GetAdUseCase getGetAdUseCase(GetAd getAd, DoesAdExist doesAdExist, UpdateAd updateAd){
        return new GetAdUseCase(getAd, doesAdExist, updateAd);
    }

    @Bean
    public FacetedSearchUseCaseV2 getFacetedSearchUseCaseV2(
            FacetedSearchByQuery facetedSearchByQuery, GetAllLocalities getLocalities, MessageSource messageSource){
        return new FacetedSearchUseCaseV2(facetedSearchByQuery, getLocalities, messageSource);
    }

    @Bean
    public CreateUserUseCase getCreateUserUseCase(CreateUser createUser, DoesUserByUsernameExists doesUserByUsernameExists){
        return new CreateUserUseCase(createUser, doesUserByUsernameExists);
    }

    @Bean
    public GetAdForEditUseCase getGetAdByOwnerUseCase(GetAdForEditUseCase.GetAdForEdit getAdForEdit, GetUserByUsername getUserByUsername,
                                                      DoesUserOwnAd doesUserOwnAd){
        return new GetAdForEditUseCase(getAdForEdit, getUserByUsername, doesUserOwnAd);
    }

    @Bean
    public GetLandlordAdsUseCase getGetAdsByOwnerAndStatusUseCase(GetAdsByOwnerAndStatus getAdsByOwnerAndStatus,
                                                                  ModelMapper modelMapper, MessageSource messageSource){
        return new GetLandlordAdsUseCase(getAdsByOwnerAndStatus, modelMapper, messageSource);
    }

    @Bean
    public StartAdCreationUseCase getStartAdCreationUseCase(CreateAd createAd,
                                                                GetAdsByOwnerAndStatusAndHousingType getAdsByOwnerAndStatusAndHousingType){
        return new StartAdCreationUseCase(createAd, getAdsByOwnerAndStatusAndHousingType);
    }

    @Bean
    public FinishAdCreationUseCase getFinishAdCreationUseCase(GetUserByUsername getUserByUsername,
                                                              UpdateAd updateAd, GetAd getAd,
                                                              DoesImageExistByAdId doesImageExistByAdId,
                                                              DoesUserOwnAd doesUserOwnAd){
        return new FinishAdCreationUseCase(getUserByUsername, updateAd, getAd, doesImageExistByAdId, doesUserOwnAd);
    }

    @Bean
    public UpdateAdUseCase getUpdateAdUseCase2(UpdateAd updateAd, GetAd getAd, GetUserByUsername getUserByUsername){
        return new UpdateAdUseCase(updateAd, getAd, getUserByUsername);
    }

    @Bean
    public UploadImageUseCase getUploadImageUseCase(
            DoesAdExist doesAdExist, GetAd getAd, CountImages countImages,
            ImageProperties imageProperties, CreateImage createImage,
            ResizeImagesService resizeImagesService){
        return new UploadImageUseCase(doesAdExist, getAd, countImages, imageProperties, createImage, resizeImagesService);
    }

    @Bean
    public DeleteImageUseCase getDeleteImageUseCase(DoesAdOwnImage doesAdOwnImage,
                                                    DoesAdExist doesAdExist, GetAd getAd, DoesImageExist doesImageExist,
                                                    GetImage getImage, DeleteImage deleteImage,
                                                    ImageProperties imageProperties
    ) {
        return new DeleteImageUseCase(doesAdExist, getAd, doesImageExist, doesAdOwnImage,
                getImage, deleteImage, imageProperties);
    }

    @Bean
    public GetImagesUseCase getGetImagesUseCase(GetImagesByAdId getImagesByAdId) {
        return new GetImagesUseCase(getImagesByAdId);
    }

    @Bean
    public SetPriceTagUseCase getSetPriceUseCase(GetPriceTagsByAdBetweenDates getPriceTagsBetweenDates,
                                                 UpdatePriceTag updatePriceTag, DeletePriceTag deletePriceTag,
                                                 CreatePriceTag createPriceTag){
        return new SetPriceTagUseCase(getPriceTagsBetweenDates, updatePriceTag, deletePriceTag, createPriceTag);
    }

    @Bean
    public GetPriceTagsByAdBetweenDatesUseCase getGetPriceTagsByAdBetweenDatesUseCase(
            GetPriceTagsByAdBetweenDates getPriceTagsBetweenDates){
        return new GetPriceTagsByAdBetweenDatesUseCase(getPriceTagsBetweenDates);
    }

    @Bean
    public ActivateAdUseCase getActivateAdUseCase(
            DoesAdExist doesAdExist, UpdateAdStatus updateAdStatus, DoesUserOwnAd doesUserOwnAd){
        return new ActivateAdUseCase(doesAdExist, updateAdStatus, doesUserOwnAd);
    }

    @Bean
    public DeactivateAdUseCase getDeactivateAdUseCase(
            DoesAdExist doesAdExist, DoesUserOwnAd doesUserOwnAd, UpdateAdStatus updateAdStatus){
        return new DeactivateAdUseCase(doesAdExist, doesUserOwnAd, updateAdStatus);
    }

    @Bean
    public SendPasswordRecoverySMSTokenUseCase getSendSMSTokenUseCase(GetUserByUsername getUserByUsername,
                                                                      DoesUserByUsernameExists doesUserByUsernameExists,
                                                                      CreatePasswordResetToken createPasswordResetToken,
                                                                      SMSService sendSMS){
        return new SendPasswordRecoverySMSTokenUseCase(getUserByUsername, doesUserByUsernameExists, createPasswordResetToken,
                sendSMS);
    }

    @Bean
    public ResetPasswordWithSMSTokenUseCase getResetPasswordWithSMSTokenUseCase(
            GetUserByUsername getUserByUsername,
            UpdateUser updateUser,
            DoesUserByUsernameExists doesUserByUsernameExists,
            DoesNotUsedSMSTokenByUserIdExists doesNotUsedSMSTokenByUserIdExists,
            GetPasswordResetTokensByUserId getPasswordResetTokensByUserId,
            PasswordEncoder passwordEncoder,
            UpdatePasswordResetToken updatePasswordResetToken){
        return new ResetPasswordWithSMSTokenUseCase(getUserByUsername, updateUser, doesUserByUsernameExists,
                doesNotUsedSMSTokenByUserIdExists, getPasswordResetTokensByUserId, passwordEncoder,
                updatePasswordResetToken);
    }

    @Bean
    public ResizeImagesService getResizeAndSaveImageFilesService(
            KafkaTemplate<String, String> kafkaTemplate) {
        return new ResizeImagesServiceImpl(kafkaTemplate);
    }

    @Bean
    public ViewAdUseCase getViewAdUseCase(ViewAdUseCase.GetAd getAd, MessageSource messageSource) {
        return new ViewAdUseCase(getAd, messageSource);
    }

    @Bean
    public GetFacetedSearchLocalitiesUseCase getGetFacetedSearchLocalitiesUseCase(GetAllLocalities getAllLocalities) {
        return new GetFacetedSearchLocalitiesUseCase(getAllLocalities);
    }

    @Bean
    public GetLocalitiesWhereAdsExistUseCase getGetLocalitiesWhereAdsExistUseCase(
            GetLocalitiesWhereAdsExistUseCase.GetLocalitiesWhereAdsExist getLocalitiesWhereAdsExist) {
        return new GetLocalitiesWhereAdsExistUseCase(getLocalitiesWhereAdsExist);
    }

    @Bean
    public GetHotAdsUseCase getGetHotAdsUseCase(
            FacetedSearchByQuery facetedSearchByQuery, MessageSource messageSource) {
        return new GetHotAdsUseCase(facetedSearchByQuery, messageSource);
    }

    @Bean
    public GetAdsCountsByStatusUseCase getGetAdsCountsByStatusUseCase(GetAdsCountsByStatusAndUsername getAdsCountsByStatus) {
        return new GetAdsCountsByStatusUseCase(getAdsCountsByStatus);
    }
}
