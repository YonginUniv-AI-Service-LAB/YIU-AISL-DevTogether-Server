package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.MatchingScrap;
import yiu.aisl.devTogether.domain.UserProfile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileScrapDto {
    private Integer id;
    private Long userProfile;
    private Integer status;
    private LocalDateTime createdAt;
    private String introduction;
    private String pr;
    private String portfolio;
    private String contents;
    private String schedule;
    private String method;
    private Integer fee;

    public ProfileScrapDto(MatchingScrap scrap) {
        this.id = scrap.getId();
        this.userProfile = scrap.getUserProfile().getUserProfileId();
        this.status = scrap.getStatus();
        this.createdAt = scrap.getCreatedAt();
        this.introduction = scrap.getUserProfile().getIntroduction();
        this.pr = scrap.getUserProfile().getPr();
        this.portfolio = scrap.getUserProfile().getPortfolio();
        this.contents = scrap.getUserProfile().getContents();
        this.schedule = scrap.getUserProfile().getSchedule();
        this.method = scrap.getUserProfile().getMethod();
        this.fee = scrap.getUserProfile().getFee();
    }
}
