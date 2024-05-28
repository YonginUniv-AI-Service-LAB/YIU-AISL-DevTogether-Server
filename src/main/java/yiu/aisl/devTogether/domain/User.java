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
import java.util.List;

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

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false,  length = 20)
    private String name;

    @Column(nullable = false,  length = 20)
    private String nickname;

    @Column(nullable = false)
    private String birth;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private RoleCategory role;


    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private GenderCategory gender;

    @Column(nullable = false, length = 255)
    @JsonIgnore
    private String pwd;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private QuestionCategory question;

    @Column(nullable = false)
    private String answer;




    @Column( length = 255)
    private Boolean img;

    @Column
    private Integer fee;

    @Column
    private String method;

    @Column
    private String location1;

    @Column
    private String location2;

    @Column
    private String location3;

    @Column
    private String subject1;
    @Column
    private String subject2;
    @Column
    private String subject3;
    @Column
    private String subject4;
    @Column
    private String subject5;
    @Column
    @ColumnDefault("0")
    private Integer push;

    @Column
    @ColumnDefault("0")
    private Integer checks;




    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;


    public User(Long id, String name, String nickname,  String email,Boolean img,  RoleCategory role, GenderCategory genderCategory, String pwd, Integer age, String method, Integer fee, String location1, String location2, String subject1, String subject2 , String subject3 , String subject4 , String subject5 , String location3 ,LocalDateTime createdAt, LocalDateTime updatedAt, Integer check) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
        this.gender = genderCategory;
        this.img = img;
        this.age = age;
        this.method = method;
        this.fee = fee;
        this.location1 = location1;
        this.location2 = location2;
        this.location3 = location3;
        this.subject1 = subject1;
        this.subject2 = subject2;
        this.subject3 = subject3;
        this.subject4 = subject4;
        this.subject5 = subject5;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.checks = check;
    }

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