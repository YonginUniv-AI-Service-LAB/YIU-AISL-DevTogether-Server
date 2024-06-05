package yiu.aisl.devTogether.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userProfile_id")
    private Long userProfileId;

    @JoinColumn
    @ManyToOne
    private User user;

    @Column
    private Integer role;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Column(columnDefinition = "TEXT")
    private String pr;

    @Column
    private String link;

    @Column
    private String contents;

    @Column(columnDefinition = "TEXT")
    private String schedule;

    @Column
    private String method;

    @Column( length = 255)
    private Boolean img;

    @Column
    private Integer fee;

    @Column
    private Boolean files;

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
    private Integer checks;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
