package kz.kaps.resort.core.domain;

import lombok.*;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PasswordResetToken {

    private Long id;

    private User user;

    private String token;

    private LocalDateTime expiryTime;

    private Boolean used;

}
