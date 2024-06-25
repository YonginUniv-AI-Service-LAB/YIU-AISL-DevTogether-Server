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
public class BoardScrap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name = "user_profile_id")
    @ManyToOne
    private UserProfile user;

    @JoinColumn(name = "board_id")
    @ManyToOne
    private Board board;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;
}
