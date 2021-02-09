package kz.kaps.resort.dataproviders.database.user;

import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.user.CreateUser;
import kz.kaps.resort.core.usecase.user.DoesUserByUsernameExists;
import kz.kaps.resort.core.usecase.user.GetUserByUsername;
import kz.kaps.resort.core.usecase.user.UpdateUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDatabaseDataProvider implements GetUserByUsername, CreateUser, UpdateUser, DoesUserByUsernameExists {

    private UserEntityRepository userEntityRepository;

    @Autowired
    public UserDatabaseDataProvider(UserEntityRepository userEntityRepository){
        this.userEntityRepository = userEntityRepository;
    }

    public User getUserByUsername(String username){
        UserEntity userEntity = userEntityRepository.findByUsername(username);
        User user = convertUserEntityToUser(userEntity);
        return user;
    }

    private User convertUserEntityToUser(UserEntity userEntity){
        return UserEntityConverter.convertUserEntityToUser(userEntity);
    }

    @Override
    public void createUser(User user) {
        UserEntity userEntity = UserEntityConverter.convertUserToUserEntity(user);
        userEntityRepository.save(userEntity);
    }

    @Override
    public boolean doesUserExistByUsername(String username) {
        return userEntityRepository.existsByUsername(username);
    }


    @Override
    public void updateUser(User user) {
        UserEntity userEntity = UserEntityConverter.convertUserToUserEntity(user);
        userEntityRepository.save(userEntity);
    }
}
