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
    private TokenDto token;
}
