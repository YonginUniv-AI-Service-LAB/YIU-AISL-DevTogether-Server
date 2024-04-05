package yiu.aisl.devTogether.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String name;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false)
    private Integer role;

    @Column(nullable = false)
    private Integer gender;



    @Column(nullable = false, length = 255)
    @JsonIgnore
    private String pwd;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    private String method;

    @Column(nullable = false)
    private Integer fee;

    @Column(nullable = false)
    private String location1;

    @Column(nullable = false)
    private String location2;

    @Column(nullable = false)
    private String location3;


    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;


    public User(Long id, String name, String email, Integer role, Integer gender, String pwd, Integer age, String method, Integer fee, String location1, String location2, String location3, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.gender = gender;

        this.pwd = pwd;
        this.age = age;
        this.method = method;
        this.fee = fee;
        this.location1 = location1;
        this.location2 = location2;
        this.location3 = location3;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
