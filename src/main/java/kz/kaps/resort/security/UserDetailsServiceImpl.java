package kz.kaps.resort.security;

import kz.kaps.resort.core.domain.User;
import kz.kaps.resort.core.usecase.user.GetUserByUsername;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDetailsServiceImpl implements UserDetailsService {

    private GetUserByUsername getUserByUsername;

    @Autowired
    public UserDetailsServiceImpl(GetUserByUsername getUserByUsername){
        this.getUserByUsername = getUserByUsername;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername.getUserByUsername(username);
        if(user == null) throw new UsernameNotFoundException("User '" + username + "' not found");

        SecurityUser securityUser = convertUserToSecurityUser(user);
        return securityUser;
    }

    private SecurityUser convertUserToSecurityUser(User user){
        SecurityUser securityUser = new SecurityUser(user.getUsername(), user.getPassword());
        return securityUser;
    }

}
