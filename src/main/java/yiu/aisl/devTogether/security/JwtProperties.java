package yiu.aisl.devTogether.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component

public class JwtProperties {

    @Value("${jwt.issuer}")
    private String issuer;   //토큰 발급자를 저장

    @Value("${jwt.secret.key}")
    private String secretKey; //토큰을 서명하기 위한 비밀키 저장
}
