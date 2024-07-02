package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yiu.aisl.devTogether.domain.UserProfile;
import yiu.aisl.devTogether.domain.state.GenderCategory;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MainDto {

    private String nickname;
    private String location1;
    private String location2;
    private String location3;
    private Integer age;
    private GenderCategory gender;
    private String method;
    private Integer fee;
    private String subject1;
    private String subject2;
    private String subject3;
    private String subject4;
    private String subject5;


    public MainDto(UserProfile userProfile) {
        this.nickname = userProfile.getNickname();
        this.location1 = userProfile.getUser().getLocation1();
        this.location2 = userProfile.getUser().getLocation2();
        this.location3 = userProfile.getUser().getLocation3();
        this.age = userProfile.getUser().getAge();
        this.gender = userProfile.getUser().getGender();
        this.method = userProfile.getMethod();
        this.fee = userProfile.getFee();
        this.subject1 = userProfile.getSubject1();
        this.subject2 = userProfile.getSubject2();
        this.subject3 = userProfile.getSubject3();
        this.subject4 = userProfile.getSubject4();
        this.subject5 = userProfile.getSubject5();
    }

    public static MainDto getMainDto(UserProfile userProfile) {
        return new MainDto(userProfile);
    }



}
