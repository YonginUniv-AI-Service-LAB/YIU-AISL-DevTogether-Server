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
    private String link;
    private String contents;
    private String schedule;
    private String method;
    private Integer fee;

    public ProfileScrapDto(MatchingScrap scrap) {
        this.id = scrap.getId();
        this.userProfile = scrap.getUserProfileId().getUserProfileId();
        this.status = scrap.getStatus();
        this.createdAt = scrap.getCreatedAt();
        this.introduction = scrap.getUserProfileId().getIntroduction();
        this.pr = scrap.getUserProfileId().getPr();
        this.link = scrap.getUserProfileId().getLink();
        this.contents = scrap.getUserProfileId().getContents();
        this.schedule = scrap.getUserProfileId().getSchedule();
        this.method = scrap.getUserProfileId().getMethod();
        this.fee = scrap.getUserProfileId().getFee();
    }
}
