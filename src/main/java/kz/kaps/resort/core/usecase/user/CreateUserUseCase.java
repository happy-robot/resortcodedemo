package kz.kaps.resort.core.usecase.user;

import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.exception.UserAlreadyExistsException;
import org.springframework.util.StringUtils;

public class CreateUserUseCase {

    private CreateUser createUser;
    private DoesUserByUsernameExists doesUserByUsernameExists;

    public CreateUserUseCase(CreateUser createUser, DoesUserByUsernameExists doesUserByUsernameExists){
        this.createUser = createUser;
        this.doesUserByUsernameExists = doesUserByUsernameExists;
    }

    public void createUser(User user) throws UserAlreadyExistsException {
        failsIfUserIsNull(user);
        failsIfUserIsInvalid(user);
        failsIfUserAlreadyExists(user.getUsername());
        createUser.createUser(user);
    }

    private void failsIfUserIsNull(User user){
        if(user == null)
            throw new NullPointerException();
    }

    private void failsIfUserIsInvalid(User user){
        if(StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword()))
            throw new IllegalArgumentException();
    }

    private void failsIfUserAlreadyExists(String username) throws UserAlreadyExistsException {
        boolean exists = doesUserByUsernameExists.doesUserExistByUsername(username);
        if(exists)
            throw new UserAlreadyExistsException();
    }
}
