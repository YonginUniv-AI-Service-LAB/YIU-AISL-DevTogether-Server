package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.Setter;
import yiu.aisl.devTogether.domain.User;


public class AskRequestDto {
    @Getter
    @Setter
    public class CreateDTO {
        private Long askId;
        private Long userId;
        private String title;
        private String contents;
        private Integer status;
        private String  answer;
        private String file;
        private Integer askCategory;



        @Override
        public String toString() {
            return "CreateDTO{" +
                    ", askId='" + askId + '\'' +
                    ", userId=" + userId +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", status=" + status +
                    ", answer='" + answer + '\'' +
                    ", file='" + file + '\'' +
                    ", askCategory=" + askCategory +

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
        private Long userId;
        private Integer role;
        @Override
        public String toString() {
            return "CreateDTO{" +
                    ", askId='" + askId + '\'' +
                    ", userId=" + userId +
                    ", role=" + role +
                    '}';

        }



    }
    @Getter
    @Setter
    public class UpdateDTO {
        private Long askId;
        private Long userId;
        private String title;
        private String contents;
        private Integer status;
        private String  answer;
        private String file;
        private Integer askCategory;
        private Integer role;


        @Override
        public String toString() {
            return "CreateDTO{" +
                    ", askId='" + askId + '\'' +
                    ", userId=" + userId +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", status=" + status +
                    ", answer='" + answer + '\'' +
                    ", file='" + file + '\'' +
                    ", askCategory=" + askCategory +
                    ", role=" + role +
                    '}';
        }
    }
}
