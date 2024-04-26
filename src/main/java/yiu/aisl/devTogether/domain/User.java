package yiu.aisl.devTogether.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 255, unique = true)
    @JsonIgnore
    private String pwd;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;


    @Column(nullable = false)
    private Integer role;

    @Column(nullable = false)
    private Integer gender;

    @Column(nullable = false)
    private String img;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false,length = 255)
    private String location1;

    @Column(nullable = false,length = 255)
    private String location2;

    @Column(nullable = false,length = 255)
    private String location3;

    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
    public void encodePwd(PasswordEncoder passwordEncoder){
        this.pwd = passwordEncoder.encode(pwd);
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public User(String email, String refreshToken) {
        this.email = email;
        this.refreshToken = refreshToken;
    }

}
