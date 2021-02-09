package kz.kaps.resort.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String username;
    private String password;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }
}
