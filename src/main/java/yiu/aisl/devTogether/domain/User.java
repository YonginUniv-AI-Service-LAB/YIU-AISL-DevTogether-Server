package yiu.aisl.devTogether.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;
import yiu.aisl.devTogether.domain.state.GenderCategory;
import yiu.aisl.devTogether.domain.state.QuestionCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;

import java.io.File;
import java.time.LocalDateTime;

@Data
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id // pk9
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = false,
            length = 255)
    private String email;

    @Column(nullable = false, length = 255)
    @JsonIgnore
    private String pwd;

    @Column(nullable = false,  length = 20)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private RoleCategory role;

    @Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private GenderCategory gender;

    @Column(nullable = false)
    private Integer age;


    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private QuestionCategory question;

    @Column(nullable = false)
    private String answer;

    @Column
    private String location1;

    @Column
    private String location2;

    @Column
    private String location3;

    @Column
    @ColumnDefault("1")
    private Integer checks;

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

    public User update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }

    public File getFile() {
        return null;
    }



}