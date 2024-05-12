package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.Setter;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.state.AskCategory;
import yiu.aisl.devTogether.domain.state.RoleCategory;

public class AskRequestDto {
    @Getter
    @Setter
    public class CreateDTO {
        private Long askId;
        private User userId;
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
        private User userId;
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
