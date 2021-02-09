package kz.kaps.resort.dataproviders.database.passwordresettoken;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PasswordResetTokenEntityRepository extends CrudRepository<PasswordResetTokenEntity, Long> {

    List<PasswordResetTokenEntity> getPasswordResetTokensByUserId(Long userId);

    boolean existsByUsedAndTokenAndUserId(boolean isUsed, String SMSToken, Long userId);

}
