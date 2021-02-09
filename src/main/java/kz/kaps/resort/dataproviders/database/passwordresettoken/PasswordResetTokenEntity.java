package kz.kaps.resort.dataproviders.database.passwordresettoken;

import kz.kaps.resort.dataproviders.database.user.UserEntity;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rsrt_password_reset_tokens")
public class PasswordResetTokenEntity {

    @Id
    @Column(name = "id_")
    @GeneratedValue
    private Long id;

    @Column(name = "token_", nullable = false)
    private String token;

    @Column(name = "expiry_time_", nullable = false)
    private LocalDateTime expiryTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_", nullable = false)
    public UserEntity user;

    @Column(name = "is_used_", nullable = false)
    private Boolean used;


    public PasswordResetTokenEntity(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }


    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }
}
