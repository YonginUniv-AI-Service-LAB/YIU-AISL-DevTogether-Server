package yiu.aisl.devTogether.dto;

import lombok.*;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.domain.UserProfile;
import yiu.aisl.devTogether.domain.state.GenderCategory;
import yiu.aisl.devTogether.repository.FilesRepository;
import yiu.aisl.devTogether.service.FilesService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {
    private Long userProfileId;
    private String name;
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
    private String nickname;
    private String subject1;
    private String subject2;
    private String subject3;
    private String subject4;
    private String subject5;
    private String location1;
    private String location2;
    private String location3;
    private Boolean img;
    private Integer scrap;
    public ProfileResponseDto(UserProfile userProfile, User user, Integer scrap) {
        this.userProfileId = userProfile.getUserProfileId();
        this.name = user.getName();
        this.introduction = userProfile.getIntroduction();
        this.pr = userProfile.getPr();
        this.portfolio = userProfile.getPortfolio();
        this.contents = userProfile.getContents();
        this.schedule = userProfile.getSchedule();
        this.method = userProfile.getMethod();
        this.fee = userProfile.getFee();
        this.createdAt = userProfile.getCreatedAt();
        this.updatedAt = userProfile.getUpdatedAt();
        this.role = userProfile.getRole();
        this.gender = user.getGender();
        this.age = user.getAge();
        this.nickname = userProfile.getNickname();
        this.subject1 = userProfile.getSubject1();
        this.subject2 = userProfile.getSubject2();
        this.subject3 = userProfile.getSubject3();
        this.subject4 = userProfile.getSubject4();
        this.subject5 = userProfile.getSubject5();
        this.location1 = user.getLocation1();
        this.location2 = user.getLocation2();
        this.location3 = user.getLocation3();
        this.img = userProfile.getFiles();
        this.scrap = scrap;
    }
}
