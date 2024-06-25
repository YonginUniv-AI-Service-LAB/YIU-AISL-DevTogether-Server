package yiu.aisl.devTogether.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.domain.state.BoardCategory;
import yiu.aisl.devTogether.domain.state.NoticeCategory;
import yiu.aisl.devTogether.dto.FilesResponseDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @JoinColumn(name = "user_profile_id")
    @ManyToOne
//    private User user;
    private UserProfile userProfile;

    @Column
    private Boolean files;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private BoardCategory Category;

    // board가 관계 주인   게시판 로드시 즉시 댓글 가져오기  보드 삭제시 댓글 자동삭제
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OrderBy("commentId asc")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "typeId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Likes> likes;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardScrap> boardScraps = new ArrayList<>();
    public List<FilesResponseDto> getFilesList() {
        return null;
    }
}
