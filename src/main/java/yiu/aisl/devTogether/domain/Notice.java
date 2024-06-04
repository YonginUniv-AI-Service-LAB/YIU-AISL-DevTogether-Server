package yiu.aisl.devTogether.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import yiu.aisl.devTogether.domain.state.NoticeCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;
import yiu.aisl.devTogether.dto.FilesResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long noticeId;

    @Column(columnDefinition = "TEXT")
    private String contents;


    @Column(nullable = false,length = 255)
    private String title;

    @Column
    private Boolean files;



    @CreationTimestamp
    @Column
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column
    private LocalDateTime updatedAt;




    @Column
    @Enumerated(EnumType.ORDINAL)
    private NoticeCategory noticeCategory;

    public List<FilesResponseDto> getFilesList() {return null;
    }
}
