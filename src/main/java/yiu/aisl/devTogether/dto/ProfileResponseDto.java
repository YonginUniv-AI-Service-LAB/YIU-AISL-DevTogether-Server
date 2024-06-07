package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.UserProfile;
import yiu.aisl.devTogether.domain.state.GenderCategory;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {
    private Long userProfileId;
    private String introduction;
    private String pr;
    private String portfolio;
    private String contents;
    private String schedule;
    private String method;
    private Integer fee;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer role;
    private GenderCategory gender;
    private Integer age;
    private String location1;
    private String location2;
    private String location3;
    private Boolean img;

    public ProfileResponseDto(UserProfile userProfile, User user) {
        this.userProfileId = userProfile.getUserProfileId();
        this.introduction = userProfile.getIntroduction();
        this.pr= userProfile.getPr();
        this.portfolio = userProfile.getPortfolio();
        this.contents = userProfile.getContents();
        this.schedule = userProfile.getSchedule();
        this.method = userProfile.getMethod();
        this.fee = userProfile.getFee();
        this.createdAt = userProfile.getCreatedAt();
        this.updatedAt = userProfile.getUpdatedAt();
        this.role = userProfile.getRole();
        this.gender = user.getGender();
        this.age=user.getAge();
        this.location1=user.getLocation1();
        this.location2=user.getLocation2();
        this.location3=user.getLocation3();
        this.img = userProfile.getImg();


    }
}
