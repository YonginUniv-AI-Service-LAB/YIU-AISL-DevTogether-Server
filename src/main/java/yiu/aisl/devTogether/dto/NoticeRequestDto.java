package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.Setter;

public class NoticeRequestDto {

    @Getter
    @Setter
    public static class CreateDTO {
        private Long noticeId;
        private String title;
        private String contents;
        private Integer category;
        private String file;

        @Override
        public String toString() {
            return "CreateDTO{" +
                    "noticeId=" + noticeId +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", category=" + category +
                    ", file='" + file + '\'' +
                    '}';
        }
    }

    @Getter
    @Setter
    public static class DeleteDTO {
        private Long noticeId;

        @Override
        public String toString() {
            return "DeleteDTO{" +
                    "noticeId=" + noticeId +
                    '}';
        }
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        private Long noticeId;
        private String title;
        private String contents;
        private Integer category;
        private String file;

        @Override
        public String toString() {
            return "UpdateDTO{" +
                    "noticeId=" + noticeId +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", category=" + category +
                    ", file='" + file + '\'' +
                    '}';
        }
    }
}
