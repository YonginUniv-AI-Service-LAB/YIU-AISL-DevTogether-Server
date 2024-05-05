package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.checkerframework.checker.units.qual.A;
import yiu.aisl.devTogether.domain.Notice;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.state.NoticeCategory;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeResponseDto {
    private Long noticeId;
    private String title;
    private String contents;
    private NoticeCategory category;
    private String file;
    private LocalDateTime createAt;
    private LocalDateTime updatedAt;


    public static NoticeResponseDto GetNoticeDTO(Notice notice) {
        return new NoticeResponseDto(
                notice.getNoticeId(),
                notice.getTitle(),
                notice.getContents(),
                notice.getCategory(),
                notice.getFile(),
                notice.getCreatedAt(),
                notice.getUpdatedAt()
        );
    }
}
