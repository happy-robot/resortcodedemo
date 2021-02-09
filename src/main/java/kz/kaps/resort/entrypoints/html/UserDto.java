package kz.kaps.resort.entrypoints.html;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;
    private String username;

    public String getPhone() {
        return username;
    }

}
