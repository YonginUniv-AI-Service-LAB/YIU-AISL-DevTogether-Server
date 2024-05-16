package yiu.aisl.devTogether.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
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
    @JoinColumn(name = "to_user_id",nullable = false)
    private User toUserId;

    @ManyToOne
    @JoinColumn(name = "from_user_id",nullable = false)
    private User fromUserId;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private StatusCategory status;
}
