package yiu.aisl.devTogether.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import yiu.aisl.devTogether.dto.FilesResponseDto;

import java.time.LocalDateTime;
import java.util.List;

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

    @JoinColumn(name = "matching_id")
    @ManyToOne
    private Matching matching;

    @Column
    private Integer role;

    @Column( length = 20)
    private String nickname;


    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Column(columnDefinition = "TEXT")
    private String pr;

    @Column(columnDefinition = "TEXT")
    private String portfolio;

    @Column
    private String contents;

    @Column(columnDefinition = "TEXT")
    private String schedule;

    @Column
    private String method;

    @Column
    private Integer fee;

    @Column
    private Boolean files;

    public FilesResponseDto getFilesdata() {
        return null;
    }

    @Column
    @ColumnDefault("0")
    private Integer checks;

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

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
