package yiu.aisl.devTogether.domain;

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
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long boardId;

    @Column(length = 50)
    private String title;

    @Column
    private String contents;

    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;

    @JoinColumn
    @ManyToOne
    private User userId;

    @Column
    private Integer check;

    @Column
    private Integer majorId;

    @Column
    private Long like;

    // board가 관계 주인   게시판 로드시 즉시 댓글 가져오기  보드 삭제시 댓글 자동삭제
    @OneToMany(mappedBy = "boardId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("commentId asc")
    private List<Comment> comments;

    @OneToMany(mappedBy = "typeId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Like> likes;
}
