package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.Setter;
import yiu.aisl.devTogether.domain.state.RoleCategory;

public class NoticeRequestDto {

    @Getter
    @Setter
    public static class CreateDTO {
        private Long noticeId;
        private Integer roleCategory;
        private String title;
        private String contents;
        private Integer noticeCategory;
        private String file;
        @Override
        public String toString() {
            return "CreateDTO{" +
                    "noticeId=" + noticeId +
                    ", roleCategory=" + roleCategory +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", noticeCategory=" + noticeCategory +
                    ", file='" + file + '\'' +
                    '}';
        }

    }

    @Getter
    @Setter
    public static class DeleteDTO {
        private Long noticeId;
        private Integer roleCategory;

    }

    @Getter
    @Setter
    public static class UpdateDTO {
        private Long noticeId;
        private Integer roleCategory;
        private String title;
        private String contents;
        private Integer noticeCategory;
        private String file;

    }
}
