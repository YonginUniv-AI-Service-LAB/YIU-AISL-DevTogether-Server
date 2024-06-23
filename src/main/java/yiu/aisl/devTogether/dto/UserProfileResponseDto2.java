package yiu.aisl.devTogether.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yiu.aisl.devTogether.domain.UserProfile;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponseDto2 {

    private Long id;
    private String nickname;
    private String pr;
    private Boolean files;
    private FilesResponseDto filesResponseDto;

    public static UserProfileResponseDto2 getUserProfile(UserProfile userProfile) {
        return new UserProfileResponseDto2(
                userProfile.getUserProfileId(),
                userProfile.getNickname(),
                userProfile.getPr(),
                userProfile.getFiles(),
                userProfile.getFilesdata()
        );
    }
}
