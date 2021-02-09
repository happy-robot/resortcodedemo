package kz.kaps.resort.core.usecase.user;

public interface DoesNotUsedSMSTokenByUserIdExists {

    boolean doesNotUsedSMSTokenByUserIdExists(String SMSToken, Long userId);

}
