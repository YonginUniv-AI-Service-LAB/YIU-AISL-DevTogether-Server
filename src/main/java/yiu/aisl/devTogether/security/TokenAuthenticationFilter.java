package yiu.aisl.devTogether.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor //클래스에 final 필드에 대한 생성자를 자동으로 생성
public class TokenAuthenticationFilter extends OncePerRequestFilter {
    //OncePerRequestFilter: Spring Security에서 제공하는 필터 클래스로, 각 요청 당 한 번만 실행
    private final TokenProvider tokenProvider;
    // TokenProvider 인터페이스 또는 클래스의 객체를 참조
    //  JWT 토큰의 생성 및 검증을 담당
    // HTTP 요청에서 토큰을 추출하고 검증할 때 사용

    private final static String HEADER_AUTHORIZATION = "Authorization";
    //HTTP 요청의 헤더 중에 인증 정보를 나타내는 헤더의 이름을 저장
    //JWT 토큰은 이 데허에 포함되어 전송

    private final static String TOKEN_PREFIX = "Bearer ";
    //JWT 토큰을 HTTP 요청의 Authorization 헤더에 넣을 때 사용되는 규칙 중 하나로,
    // "Bearer "와 같이 토큰의 유형을 나타내는 특정 문자열을 앞에 붙임

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, //라이언트가 보낸 HTTP 요청에 대한 정보를 담고 있는 객체
            HttpServletResponse response, //서버가 클라이언트에게 보낼 HTTP 응답에 대한 정보를 담고 있는 객체
            FilterChain filterChain)  throws ServletException, IOException //필터들의 체인을 나타내며, 현재 필터가 요청을 처리한 후 다음 필터로 요청을 전달하는 역할
    {
        String authorizationHeader = request.getHeader(HEADER_AUTHORIZATION); // HTTP 요청 헤더에서 "Authorization" 헤더 값을 가져와서
        String token = getAccessToken(authorizationHeader); //그것을 통해 인증 토큰을 추출하는 과정



        if (token != null && tokenProvider.validToken(token)) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            System.out.println("권한 : " +authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
        //추출한 토큰이 null이 아니고, 토큰이 유효한 경우에만 인증 객체를 가져와서
        // Spring Security의 SecurityContextHolder에 설정
        // 요청을 필터 체인에 다음 필터로 전달

        // 즉 클라이언트가 제공한 토은을 사용하여 사용자를 인증, 그 사용자의 인증 정보를 보안 컨텍스트에 설정한 후 요청 처리


    }



    private String getAccessToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return authorizationHeader.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    // 인증헤더가 null이 아니고, 헤어닥 Bearer로 시작한다면 해당 헤더에서 Bearer를 제외한 토큰 부분만 반환
    // 그렇지 않은 경우에 null 반환

    // 즉 클라이언트가 보낸 인증 헤더에서 실체 토큰 값을 추출하여 반환하는 역할을 함
}