package yiu.aisl.devTogether.dto;

import lombok.Getter;

import lombok.Setter;




public class NoticeRequestDto {


    @Getter
    @Setter
    public class CreateDTO {
        private Long noticeId;
        private String title;
        private String contents;
        private Integer category;
        private String file;


    }
    @Getter
    @Setter
    public class DeleteDTO {
        private Long noticeId;


    }
    @Getter
    @Setter
    public class UpdateDTO {
        private Long noticeId;
        private String title;
        private String contents;
        private Integer category;
        private String file;



    }
}
