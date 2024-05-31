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
import yiu.aisl.devTogether.domain.state.RoleCategory;
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







    private long accessTokenValidTime = Duration.ofMinutes(30).toMillis(); // 만료시간 30분
    private long refreshTokenValidTime = Duration.ofDays(14).toMillis(); // 만료시간 2주

    private final JpaUserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(salt.getBytes(StandardCharsets.UTF_8));
    }


    public String createToken(User user, Integer role) {
        Date now = new Date();

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuer(jwtProperties.getIssuer())
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidTime))
                .setSubject(user.getNickname())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .claim("nickname", user.getNickname())
                .signWith(secretKey, SignatureAlgorithm.HS256);


        if (role == 0) {
            jwtBuilder.claim("role", "ADMIN");
            System.out.println("관리자 권한 부여");
        } else if (role == 1) {
            jwtBuilder.claim("role", "MENTOR");
            System.out.println("멘토 권한 부여");
        } else if(role == 2) {
            jwtBuilder.claim("role", "MENTEE");
            System.out.println("멘티 권한 부여");
        }

        return jwtBuilder.compact();
    }


    public Authentication getAuthentication(String token) {
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByEmail(this.getEmail(token));
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }



    public String getEmail(String token) {
        Claims claims = getClaims(token);
        return claims.get("email", String.class);


    }


    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }


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
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public TokenInfo getTokenInfo(String token) {
        Claims body = getClaims(token);
        Set<String> keySet = body.keySet();

        String email = body.get("email", String.class);
        String nickname = body.get("nickname", String.class);
        Date issuedAt = body.getIssuedAt();
        Date expiration = body.getExpiration();
        return new TokenInfo(email, nickname, issuedAt, expiration);
    }



    @Getter
    public class TokenInfo {
        private String email;
        private String nickname;
        private Date issuedAt;
        private Date expire;

        public TokenInfo(String email, String nickname, Date issuedAt, Date expire) {
            this.email = email;
            this.nickname = nickname;
            this.issuedAt = issuedAt;
            this.expire = expire;
        }
    }

}
