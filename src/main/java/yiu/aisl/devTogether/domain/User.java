package yiu.aisl.devTogether.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;
import yiu.aisl.devTogether.domain.state.GenderCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;

import java.time.LocalDateTime;

@Data
@Entity
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

    @Column(nullable = false,  length = 255)
    private String img;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private RoleCategory role;


    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private GenderCategory genderCategory;

    @Column(nullable = false)
    private Integer fee;

    @Column(nullable = false, length = 255)
    @JsonIgnore
    private String pwd;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String method;



    @Column(nullable = false)
    private String location1;

    @Column(nullable = false)
    private String location2;

    @Column(nullable = false)
    private String location3;

    @Column(nullable = false)
    private String subject1;
    @Column(nullable = false)
    private String subject2;
    @Column(nullable = false)
    private String subject3;
    @Column(nullable = false)
    private String subject4;
    @Column(nullable = false)
    private String subject5;
    @Column(columnDefinition = "TEXT")
    private String refreshToken;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;


    public User(Long id, String name, String nickname,  String email,String img,  RoleCategory role, GenderCategory genderCategory, String pwd, Integer age, String method, Integer fee, String location1, String location2, String subject1, String subject2 , String subject3 , String subject4 , String subject5 , String location3 ,LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
        this.name = name;
        this.nickname = nickname;
        this.role = role;
        this.genderCategory = genderCategory;
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
}
