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

    @Column(columnDefinition = "TEXT")
    private String contents;

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
