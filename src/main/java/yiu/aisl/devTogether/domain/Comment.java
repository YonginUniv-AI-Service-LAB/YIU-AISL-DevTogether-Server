package yiu.aisl.devTogether.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long commentId;

    @JoinColumn(name = "board_id")
    @ManyToOne
    @JsonBackReference
    private Board board;

    @JoinColumn(name = "user_profile_id")
    @ManyToOne
    private UserProfile userProfile;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @OneToMany(mappedBy = "typeId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Likes> likes;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;
}
