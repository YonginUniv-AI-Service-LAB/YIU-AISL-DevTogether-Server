package yiu.aisl.devTogether.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.UserProfile;

@Data
@NoArgsConstructor
public class ReviewRequestDto {
    @Getter
    @Setter
    public class creatDto {
        public Long matchingId;
        public String contents;
        public Boolean hide;
        public Integer category;
    }

    @Getter
    @Setter
    public class hideDto {
        public Long review_id;
        public Boolean hide;
    }
}
