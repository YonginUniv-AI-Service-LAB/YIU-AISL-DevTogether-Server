package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import yiu.aisl.devTogether.domain.state.BoardCategory;

import java.util.List;

public class BoardRequestDto {
    @Getter
    @Setter
    public class DetailDto {
        private Long boardId;
    }

    @Getter
    @Setter
    public static class CreateDto {
        private String title;
        private String contents;
        private Integer category;
//        private List<MultipartFile> files;
    }

    @Getter
    @Setter
    public class UpdateDto {
        private Long boardId;
        private String title;
        private String contents;
        private Integer category;
        private List<Long> deleteId;
    }

    @Getter
    @Setter
    public class DeleteDto {
        private Long boardId;

    }

    @Getter
    @Setter
    public class likeDto {
        private Long boardId;
        private Boolean count;
    }

    @Getter
    @Setter
    public class CreatScrapDto {
        private Long boardId;
    }

    //----------------댓글--------------------
    @Getter
    @Setter
    public class CreateCommentDto {
        private Long boardId;
        private String contents;
    }

    @Getter
    @Setter
    public class UpdateCommentDto {
        private Long commentId;
        private String contents;
    }

    @Getter
    @Setter
    public class DeleteCommentDto {
        private Long commentId;
    }

    @Getter
    @Setter
    public class likeCommentDto {
        private Long commentId;
        private Boolean count;

    }

}
