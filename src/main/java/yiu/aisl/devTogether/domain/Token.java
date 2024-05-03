package yiu.aisl.devTogether.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import java.util.concurrent.TimeUnit;

@RedisHash("refreshToken")        //Redis에 저장될 데이터의 해시 타입 정의  > Redis에 저장될 때 refreshToken  해시 키를 사용하여 저장
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor



public class Token {


    @Id
    @JsonIgnore //  해당 필드를 JSON 직렬화/ 역직렬화 대상 제외  > 보안상이나 데이터 전송 효율성을 고려할 때 사용
    private String email;
    private String refreshToken;

    @TimeToLive(unit = TimeUnit.SECONDS)   //토큰 만료 시간   unit 속성은 시간 단위를 지정
    private  Long expiration;

    public void setExpiration(Long expiration){this.expiration = expiration;}

}