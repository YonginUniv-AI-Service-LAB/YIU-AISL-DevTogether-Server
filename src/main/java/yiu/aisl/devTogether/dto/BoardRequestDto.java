package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.Setter;

public class BoardRequestDto {
    @Getter
    @Setter
    public class DetailDto{
        private Long boardID;
    }
    @Getter
    @Setter
    public static class CreateDto{
        private String title;
        private String contents;
        private String file;
        private Long userId;
    }
    @Getter
    @Setter
    public class DeleteDto{
        private Long BoardId;

    }
    @Getter
    @Setter
    public class UpdateDto{
        private Long BoardId;
        private String title;
        private String contents;
        private String file;
    }
    @Getter
    @Setter
    public class likeDto {
        private Long BoardId;
        private Long like;
    }

}
