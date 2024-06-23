package yiu.aisl.devTogether.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {

    private String email;
    private String name;
    private String nickname;
    private Integer role;
    private Long user_profile_id;
    private TokenDto token;
}