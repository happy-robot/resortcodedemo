package kz.kaps.resort.dataproviders.database.user;

import org.springframework.data.repository.CrudRepository;

interface UserEntityRepository extends CrudRepository<UserEntity, Long> {

    UserEntity findByUsername(String username);

    boolean existsByUsername(String username);

}
