package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.Setter;

public class FaqRequestDto {

    @Getter
    @Setter
    public static class CreateDTO {

        private String title;
        private String contents;

        @Override
        public String toString() {
            return "CreateDTO{" +
                    "title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    '}';
        }
    }

    @Getter
    @Setter
    public static class DeleteDTO {
        private Long faqId;
    }

    @Getter
    @Setter
    public static class UpdateDTO {
        private Long faqId;
        private String title;
        private String contents;

        @Override
        public String toString() {
            return "UpdateDTO{" +
                    "faqId='" + faqId + '\'' +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    '}';
        }
    }
}
