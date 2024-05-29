package yiu.aisl.devTogether.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import yiu.aisl.devTogether.domain.User;

@Data
@NoArgsConstructor
public class ReportRequestDto {
    @Data
    public class creatDto {
        private Long toUserId;
        private Integer type;
        private Long typeId;
        private Integer category;
        private String contents;
    }

    @Data
    public class treatDto {
        public Long reportId;
        public Integer status;
    }
}
