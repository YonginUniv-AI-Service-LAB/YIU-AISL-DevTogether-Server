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
    @Column
    private Long matchingId;

    @ManyToOne
    @JoinColumn(name = "user_mentor_id")
    private UserProfile mentor;

    @ManyToOne
    @JoinColumn(name = "user_mentee_id")
    private UserProfile mentee;




    @Column(nullable = false)
    private MatchingCategory matchingCategory;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private StatusCategory status;

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
