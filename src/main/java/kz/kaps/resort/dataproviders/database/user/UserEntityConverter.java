package kz.kaps.resort.dataproviders.database.user;

import kz.kaps.resort.core.domain.User;

public class UserEntityConverter {

    public static UserEntity convertUserToUserEntity(User user){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(user.getPassword());
        return userEntity;
    }

    public static User convertUserEntityToUser(UserEntity userEntity){
        User user = new User(userEntity.getUsername(), userEntity.getPassword());
        user.setId(userEntity.getId());
        return user;
    }

}
