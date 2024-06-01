package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class NoticeRequestDto {

    @Getter
    @Setter
    public static class CreateDTO {
        private String title;
        private String contents;
        private Integer noticeCategory;
        //private Boolean files;
        @Override
        public String toString() {
            return "CreateDTO{" +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", noticeCategory=" + noticeCategory +
                    // ", files='" + files + '\'' +
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
        private Integer noticeCategory;
        private List<Long> deleteId;

        @Override
        public String toString() {
            return "UpdateDTO{" +
                    "noticeId=" + noticeId +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", noticeCategory=" + noticeCategory +
                    ", deleteId=" + deleteId +
                    '}';
        }
    }


}
