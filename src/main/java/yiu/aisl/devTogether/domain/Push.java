package yiu.aisl.devTogether.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import yiu.aisl.devTogether.domain.state.PushCategory;

import java.time.LocalDateTime;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Push {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long pushId;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    @Column
    private PushCategory type;

    @Column
    private Long targetId;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
