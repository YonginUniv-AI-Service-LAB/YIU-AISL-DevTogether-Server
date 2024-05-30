package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Notice;
import yiu.aisl.devTogether.domain.state.NoticeCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponseDto {
    private Long noticeId;
    private RoleCategory role;
    private String title;
    private String contents;
    private NoticeCategory noticeCategory;
    private Boolean files;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static NoticeResponseDto GetNoticeDTO(Notice notice) {
        return new NoticeResponseDto(

                notice.getNoticeId(),
                notice.getRole(),
                notice.getTitle(),
                notice.getContents(),
                notice.getNoticeCategory(),
                notice.getFiles(),
                notice.getCreatedAt(),
                notice.getUpdatedAt()
        );
    }


}
