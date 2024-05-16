package yiu.aisl.devTogether.dto;
import lombok.Getter;
import lombok.Setter;




public class MatchingRequestDto {
    @Getter
    @Setter
    public class ApplyDTO {
        private String fromUserId;




        @Override
        public String toString() {
            return "ApplyDTO{" +
                    ", title='" + fromUserId + '\'' +

                    '}';
        }


    }
    @Getter
    @Setter
    public class AproveDTO {
        private Long askId;
        private String  answer;
        private Integer role;


        @Override
        public String toString() {
            return "AproveDTO{" +
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
        private String  answer;
        private Integer role;


        @Override
        public String toString() {
            return "DeleteDTO{" +
                    ", askId='" + askId + '\'' +
                    ", answer='" + answer + '\'' +
                    ", role=" + role +
                    '}';
        }


    }
    @Getter
    @Setter
    public class RefusalDTO {
        private Long askId;
        private Integer role;
        @Override
        public String toString() {
            return "RefusalDTO{" +
                    ", askId='" + askId + '\'' +
                    ", role=" + role +
                    '}';

        }



    }


    @Getter
    @Setter
    public class ConfirmDTO {
        private Long askId;
        private Integer role;
        @Override
        public String toString() {
            return "ConfirmDTO{" +
                    ", askId='" + askId + '\'' +
                    ", role=" + role +
                    '}';

        }



    }
    @Getter
    @Setter
    public class EndDTO {
        private Long askId;
        private String title;
        private String contents;
        private Integer status;
        private String  answer;
        private String file;
        private Integer askCategory;
        private Integer role;


        @Override
        public String toString() {
            return "EndDTO{" +
                    ", askId='" + askId + '\'' +
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
