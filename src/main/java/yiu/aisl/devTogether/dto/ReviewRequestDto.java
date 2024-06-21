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
        public Integer star1;
        public Integer star2;
        public Integer star3;
    }

    @Getter
    @Setter
    public class hideDto {
        public Long review_id;
        public Boolean hide;
    }
}
