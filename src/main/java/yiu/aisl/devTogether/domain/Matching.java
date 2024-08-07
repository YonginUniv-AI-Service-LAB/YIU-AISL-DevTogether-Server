package yiu.aisl.devTogether.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchingId;

    @ManyToOne
    @JoinColumn(name = "mentor_user_id")
    private UserProfile mentor;

    @ManyToOne
    @JoinColumn(name = "mentee_user_id")
    private UserProfile mentee;

    @Column
    private String status;

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
    private Integer tutoringFee;

    @Column
    private String location1;

    @Column
    private String location2;

    @Column
    private String location3;

    @Column
    private String method;

    @Column
    private String schedule;

    @Column(columnDefinition = "TEXT")
    private String contents;
    // 1 = 멘토만 , 2 = 멘티만, 3 = 둘다
    @Column
    private Integer checkReview;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime endedAt;



}
