package yiu.aisl.devTogether.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import yiu.aisl.devTogether.config.CustomUserDetails;
import yiu.aisl.devTogether.domain.User;
import yiu.aisl.devTogether.service.JpaUserDetailsService;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Component
public class TokenProvider {

    private final JwtProperties jwtProperties;

    @Value("${jwt.secret.key}")
    private String salt;

    private Key secretKey;

    @Value("${admin.Id1}")
    private Long admin1;
    @Value("${admin.Id2}")
    private Long admin2;



    // 만료시간 : 30분
    //    private final long exp = 500L * 60 * 60;

    private long accessTokenValidTime = Duration.ofMinutes(30).toMillis(); // 만료시간 30분
    private long refreshTokenValidTime = Duration.ofDays(14).toMillis(); // 만료시간 2주

    private final JpaUserDetailsService userDetailsService;

    @PostConstruct  //해당 메서드가 객체 생성 이후에 실행되어야함

    //init() 메서드에 @PostConstruct 애노테이션이 지정되어 있으므로, 해당 클래스의 객체가 생성된 후 init()매서드가 호출되어 secretKey를 초기화함
    // 이 초기화 작업은 객체가 사용될 준비가 완료된 후에 실행됨
    //secretKey를 초기화하는 코드는 HMAC-SHA 키를 생성하는데 사용되며 salt문자열을 이용하여 StandardCharsets.UTF_8 문자 인코딩을 적용하여 키를 생성
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }

    // 토큰 생성
    public String createToken(User user) {

        // now: 현재 시간을 나타내는 변수
        Date now = new Date();

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())   //setIssuer(): 발급자를 설정
                .setIssuedAt(now)  //토큰 발급 시간을 설정
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidTime))
                //setExpiration(): 토큰 만료 시간을 설정
                // 현재 시간에서 accessTokenValidTime 만큼 시간이 지난 후의 시간으로 설정


                .setSubject(user.getName()) //토큰의 주제를 설정
                .claim("email", user.getEmail())
                .claim("name", user.getName())
                .signWith(secretKey, SignatureAlgorithm.HS256); //signWith()를 사용하여 jwt에 서명을 추가함
                //서명에는 jwt를 검증하기 위한 비밀키인 secretKey와 암호와 알고리즘 SignatureAlgorithm.HS256을 사용

        if(user.getId().equals(admin1)
                || user.getId().equals(admin2))
            jwtBuilder.claim("role", "ADMIN");
        else jwtBuilder.claim("role", "USER");

        return jwtBuilder.compact();
    }

    //사용자 인증 정보를 가져오는 메서드

    public Authentication getAuthentication(String token) {
        // getAuthentication(String token) 메서드는 JWT 토큰을 받아서 해당 토큰에 대한 사용자 인증 정보를 반환
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByEmail(this.getEmail(token));

        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());

    }



    // 토큰에 담겨있는 유저 account 획득
    public String getEmail(String token) {
        Claims claims = getClaims(token);
        return claims.get("email", String.class);
    }

    // Authorization Header를 통해 인증을 한다.
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            // Bearer 검증
            if (!token.substring(0, "BEARER ".length()).equalsIgnoreCase("BEARER ")) {
                return false;
            } else {
                token = token.split(" ")[1].trim();
            }
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(token);
            // 만료되었을 시 false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey).build()
                    .parseClaimsJws(token);
            // 만료되었을 시 false
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) { // 복호화 과정에서 에러가 나면 유효하지 않은 토큰
            System.out.println("복호화 에러: "+ e.getMessage());
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser() // 클레임 조회
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰 정보 리턴
    public TokenInfo getTokenInfo(String token) {
        Claims body = getClaims(token);
        Set<String> keySet = body.keySet();
        for (String s : keySet) {
           System.out.println("s = " + s);
       }

        String email = body.get("email", String.class);
        String name = body.get("name", String.class);
        Date issuedAt = body.getIssuedAt();
        Date expiration = body.getExpiration();
        return new TokenInfo(email, name, issuedAt, expiration);
    }


    @Getter
    public class TokenInfo {
        private String email;
        private String name;
        private Date issuedAt;
        private Date expire;

        public TokenInfo(String email, String name, Date issuedAt, Date expire) {
            this.email = email;
            this.name = name;
            this.issuedAt = issuedAt;
            this.expire = expire;
        }
    }

}

