package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


public class AskRequestDto {
    @Getter
    @Setter
    public static class CreateDTO {
        private String title;
        private String contents;
        private Integer askCategory;



        @Override
        public  String toString() {
            return "CreateDTO{" +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", ask_category=" + askCategory +
                    '}';
        }


    }
    @Getter
    @Setter
    public static class AnswerDTO {
        private Long askId;
        private String  answer;


        @Override
        public String toString() {
            return "CreateDTO{" +
                    ", askId='" + askId + '\'' +
                    ", answer='" + answer + '\'' +
                    '}';
        }


    }



    @Getter
    @Setter
    public static class DeleteDTO {
        private Long askId;

        @Override
        public String toString() {
            return "CreateDTO{" +
                    ", askId='" + askId + '\'' +
                    '}';

        }



    }
    @Getter
    @Setter
    public static class UpdateDTO {
        private Long askId;
        private String title;
        private String contents;
        private Integer status;
        private String  answer;
        private Integer askCategory;
        private List<Long> deleteId;

        @Override
        public String toString() {
            return "CreateDTO{" +
                    ", askId='" + askId + '\'' +
                    ", title='" + title + '\'' +
                    ", contents='" + contents + '\'' +
                    ", status=" + status +
                    ", answer='" + answer + '\'' +
                    ", askCategory=" + askCategory +
                    ", deleteId=" + deleteId +
                    '}';
        }
    }
}
