package kz.kaps.resort.dataproviders.database.passwordresettoken;

import kz.kaps.resort.core.domain.PasswordResetToken;
import kz.kaps.resort.core.usecase.user.CreatePasswordResetToken;
import kz.kaps.resort.core.usecase.user.DoesNotUsedSMSTokenByUserIdExists;
import kz.kaps.resort.core.usecase.user.GetPasswordResetTokensByUserId;
import kz.kaps.resort.core.usecase.user.UpdatePasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PasswordResetTokenDatabaseDataProvider implements CreatePasswordResetToken,
        GetPasswordResetTokensByUserId, DoesNotUsedSMSTokenByUserIdExists,
        UpdatePasswordResetToken {

    private PasswordResetTokenEntityRepository repository;

    @Autowired
    public PasswordResetTokenDatabaseDataProvider(PasswordResetTokenEntityRepository repository){
        this.repository = repository;
    }

    @Override
    public void createPasswordResetToken(PasswordResetToken token) {
        PasswordResetTokenEntity entity = PasswordResetTokenConverter.toEntity(token);
        repository.save(entity);
    }

    @Override
    public List<PasswordResetToken> getPasswordResetTokensByUserId(Long userId) {
        List<PasswordResetTokenEntity> tokenEntities = repository.getPasswordResetTokensByUserId(userId);
        List<PasswordResetToken> tokens = tokenEntities.stream()
                .map(e -> PasswordResetTokenConverter.toDomain(e))
                .collect(Collectors.toList());

        return tokens;
    }

    @Override
    public boolean doesNotUsedSMSTokenByUserIdExists(String SMSToken, Long userId) {
        boolean isUsed = false;
        return repository.existsByUsedAndTokenAndUserId(isUsed, SMSToken, userId);
    }

    @Override
    public void updatePasswordResetToken(PasswordResetToken token) {
        PasswordResetTokenEntity entity = PasswordResetTokenConverter.toEntity(token);
        repository.save(entity);
    }
}
