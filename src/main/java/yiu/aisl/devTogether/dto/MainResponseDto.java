package yiu.aisl.devTogether.dto;

import yiu.aisl.devTogether.domain.UserProfile;

public class MainResponseDto {
    private Long id;
    private String nickname;
    private String pr;
    private String introduction;
    private Boolean files;
    private FilesResponseDto filesResponseDto;

    public static UserProfileResponseDto2 getUserProfile(UserProfile userProfile) {
        return new UserProfileResponseDto2(
                userProfile.getUserProfileId(),
                userProfile.getNickname(),
                userProfile.getPr(),
                userProfile.getIntroduction(),
                userProfile.getFiles(),
                userProfile.getFilesdata()
        );
    }


}
