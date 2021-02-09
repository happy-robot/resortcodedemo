package kz.kaps.resort.core.usecase.user;

import kz.kaps.resort.core.domain.User;

public interface GetUserByUsername {

    User getUserByUsername(String username);

}
