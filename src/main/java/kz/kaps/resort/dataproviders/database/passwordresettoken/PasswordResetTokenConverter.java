package kz.kaps.resort.dataproviders.database.passwordresettoken;

import kz.kaps.resort.core.domain.PasswordResetToken;
import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.dataproviders.database.user.UserEntity;
import kz.kaps.resort.dataproviders.database.user.UserEntityConverter;

public class PasswordResetTokenConverter {

    public static PasswordResetTokenEntity toEntity(PasswordResetToken token){
        UserEntity user = UserEntityConverter.convertUserToUserEntity(token.getUser());

        PasswordResetTokenEntity entity = new PasswordResetTokenEntity();
        entity.setId(token.getId());
        entity.setToken(token.getToken());
        entity.setExpiryTime(token.getExpiryTime());
        entity.setUser(user);
        entity.setUsed(token.getUsed());

        return entity;
    }

    public static PasswordResetToken toDomain(PasswordResetTokenEntity entity){
        User user = UserEntityConverter.convertUserEntityToUser(entity.getUser());
        PasswordResetToken token = PasswordResetToken.builder()
                .id(entity.getId())
                .token(entity.getToken())
                .expiryTime(entity.getExpiryTime())
                .user(user)
                .used(entity.getUsed())
                .build();
        return token;
    }

}
