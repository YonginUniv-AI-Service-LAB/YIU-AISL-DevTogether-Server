package yiu.aisl.devTogether.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.state.QuestionCategory;

@Getter
@Setter
@NoArgsConstructor
public class EmailDto {


    private String name;
    private Integer question;
    private String answer;
    private String birth;
}






