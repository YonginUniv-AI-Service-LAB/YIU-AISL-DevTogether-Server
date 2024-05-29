package yiu.aisl.devTogether.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchingScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "user_profile_id")
    @ManyToOne
    private UserProfile userProfileId;

    @Column(nullable = false)
    private Integer status;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;
}
