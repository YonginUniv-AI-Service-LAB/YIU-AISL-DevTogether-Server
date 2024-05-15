package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class BoardRequestDto {
    @Getter
    @Setter
    public class DetailDto{
        private Long boardId;
    }
    @Getter
    @Setter
    public static class CreateDto{
        private String title;
        private String contents;
//        private List<MultipartFile> files;
    }
    @Getter
    @Setter
    public class DeleteDto{
        private Long boardId;

    }
    @Getter
    @Setter
    public class UpdateDto{
        private Long boardId;
        private String title;
        private String contents;
        private String file;
    }
    @Getter
    @Setter
    public class likeDto {
        private Long boardId;
        private Long likeId;
        private Boolean count;
        //private int type;
    }
    @Getter
    @Setter
    public class CreateCommentDto {
        private Long boardId;
        private String contents;
    }
    @Getter
    @Setter
    public class DeleteCommentDto{
        private Long boardId;
        private Long CommentId;
    }
    @Getter
    @Setter
    public class CreatScrapDto{
        private Long boardId;
    }
}
