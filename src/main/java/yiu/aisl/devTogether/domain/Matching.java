package yiu.aisl.devTogether.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import yiu.aisl.devTogether.domain.state.MatchingCategory;
import yiu.aisl.devTogether.domain.state.StatusCategory;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Matching {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "matching_id")
    private Long matchingId;

    @ManyToOne
    @JoinColumn(name = "mentor_user_id")
    private UserProfile mentor;

    @ManyToOne
    @JoinColumn(name = "mentee_user_id")
    private UserProfile mentee;

    @Column
    private String status;

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
