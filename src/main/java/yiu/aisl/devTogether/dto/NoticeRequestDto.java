package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.Setter;


public class NoticeRequestDto {

    @Getter
    @Setter
    public static class CreateDTO {
        private Long noticeId;
        private Integer role;
        private String title;
        private String contents;
        private Integer noticeCategory;
        private Boolean files;
        @Override
        public String toString() {
            return "CreateDTO{" +
                    "noticeId=" + noticeId +
                    ", roleCategory=" + role +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", noticeCategory=" + noticeCategory +
                    ", files='" + files + '\'' +
                    '}';
        }

    }
    @Getter
    @Setter
    public static class DetailDTO {
        private Long noticeId;

        @Override
        public String toString() {
            return "DetailDTO{" +
                    "noticeId=" + noticeId +
                    '}';
        }

    }



    @Getter
    @Setter
    public static class DeleteDTO {
        private Long noticeId;
        private Integer role;
        @Override
        public String toString() {
            return "DeleteDTO{" +
                    "noticeId=" + noticeId +
                    ", roleCategory=" + role +
                    '}';
        }
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        private Long noticeId;
        private Integer role;
        private String title;
        private String contents;
        private Integer noticeCategory;

        @Override
        public String toString() {
            return "UpdateDTO{" +
                    "noticeId=" + noticeId +
                    ", roleCategory=" + role +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", noticeCategory=" + noticeCategory +

                    '}';
        }
    }


}
