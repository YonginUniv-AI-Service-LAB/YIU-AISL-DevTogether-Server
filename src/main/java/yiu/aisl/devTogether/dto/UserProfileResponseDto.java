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
public class UserProfileResponseDto {
    private Long id;
    private String introduction;
    private String pr;
    private String portfolio;
    private String contents;
    private String schedule;
    private String method;
    private Boolean img;
    private Integer fee;
    private String subject1;
    private String subject2;
    private String subject3;
    private String subject4;
    private String subject5;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserProfileResponseDto(UserProfile userProfile) {
        this.id = userProfile.getUserProfileId();
        this.introduction = userProfile.getIntroduction();
        this.pr = userProfile.getPr();
        this.portfolio = userProfile.getPortfolio();
        this.contents = userProfile.getContents();
        this.schedule = userProfile.getSchedule();
        this.method = userProfile.getMethod();
        this.img = userProfile.getFiles();
        this.fee = userProfile.getFee();
        this.subject1 = userProfile.getSubject1();
        this.subject2 = userProfile.getSubject2();
        this.subject3 = userProfile.getSubject3();
        this.subject4 = userProfile.getSubject4();
        this.subject5 = userProfile.getSubject5();
        this.createdAt = userProfile.getCreatedAt();
        this.updatedAt = userProfile.getUpdatedAt();
    }
}
