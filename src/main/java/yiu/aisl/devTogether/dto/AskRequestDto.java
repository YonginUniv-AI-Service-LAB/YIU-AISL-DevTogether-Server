package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.Setter;



public class AskRequestDto {
    @Getter
    @Setter
    public class CreateDTO {
        private String title;
        private String contents;
        private Boolean files;
        private Integer askCategory;



        @Override
        public String toString() {
            return "CreateDTO{" +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", files='" + files + '\'' +
                    ", ask_category=" + askCategory +
                    '}';
        }


    }
    @Getter
    @Setter
    public class AnswerDTO {
        private Long askId;
        private String  answer;
        private Integer role;


        @Override
        public String toString() {
            return "CreateDTO{" +
                    ", askId='" + askId + '\'' +
                    ", answer='" + answer + '\'' +
                    ", role=" + role +
                    '}';
        }


    }



    @Getter
    @Setter
    public class DeleteDTO {
        private Long askId;
        private Integer role;
        @Override
        public String toString() {
            return "CreateDTO{" +
                    ", askId='" + askId + '\'' +
                    ", role=" + role +
                    '}';

        }



    }
    @Getter
    @Setter
    public class UpdateDTO {
        private Long askId;
        private String title;
        private String contents;
        private Integer status;
        private String  answer;

        private Integer askCategory;
        private Integer role;


        @Override
        public String toString() {
            return "CreateDTO{" +
                    ", askId='" + askId + '\'' +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", status=" + status +
                    ", answer='" + answer + '\'' +

                    ", askCategory=" + askCategory +
                    ", role=" + role +
                    '}';
        }
    }
}
