package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.MatchingScrap;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchingScrapDto {

    private Integer id;
    private Integer matching;
    private String title;
    private String contents;
    private LocalDateTime createAt;




}
