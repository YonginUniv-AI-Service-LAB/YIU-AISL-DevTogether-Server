package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.UserProfile;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {
    private Long userProfileId;
    private String introduction;
    private String pr;
    private String link;
    private String contents;
    private String schedule;
    private String method;
    private Integer fee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer role;

    public ProfileResponseDto(UserProfile userProfile) {
        this.userProfileId = userProfile.getUserProfileId();
        this.introduction = userProfile.getIntroduction();
        this.pr= userProfile.getPr();
        this.link = userProfile.getLink();
        this.contents = userProfile.getContents();
        this.schedule = userProfile.getSchedule();
        this.method = userProfile.getMethod();
        this.fee = userProfile.getFee();
        this.createdAt = userProfile.getCreatedAt();
        this.updatedAt = userProfile.getUpdatedAt();
        this.role = userProfile.getRole();
    }
}
