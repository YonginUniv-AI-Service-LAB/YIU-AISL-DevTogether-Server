package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.Board;
import yiu.aisl.devTogether.domain.BoardScrap;
import yiu.aisl.devTogether.domain.Likes;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardScrapDto {
    private Integer id;
    private Integer board;
    private String title;
    private String contents;
    private Integer likes;
    private LocalDateTime createdAt;

    public BoardScrapDto(BoardScrap scrap) {
        this.id = scrap.getId();
        this.board = Math.toIntExact(scrap.getBoard().getBoardId());
        this.title = scrap.getBoard().getTitle();
        this.contents = scrap.getBoard().getContents();
        this.createdAt = scrap.getCreatedAt();
        this.likes = scrap.getBoard().getLikes().size();
    }
}
