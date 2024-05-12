package yiu.aisl.devTogether.dto;


import lombok.Getter;
import lombok.Setter;



public class FaqRequestDto {

    @Getter
    @Setter
    public class CreateDTO {
        private Long faqId;
        private Integer role;
        private String title;
        private String contents;
        @Override
        public String toString() {
            return "CreateDTO{" +
                    ", faqId='" + faqId + '\'' +
                    ", roleCategory=" + role +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    '}';
        }


    }
    @Getter
    @Setter
    public class DeleteDTO {
        private Long faqId;
        private Integer role;



    }
    @Getter
    @Setter
    public class UpdateDTO {
        private Long faqId;
        private Integer role;
        private String title;
        private String contents;
        @Override
        public String toString() {
            return "UpdateDTO{" +
                    ", faqId='" + faqId + '\'' +
                    ", roleCategory=" + role +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    '}';
        }


    }
}
