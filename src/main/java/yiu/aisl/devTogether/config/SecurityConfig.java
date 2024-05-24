package yiu.aisl.devTogether.config;

import com.google.firebase.database.annotations.NotNull;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import yiu.aisl.devTogether.security.TokenAuthenticationFilter;
import yiu.aisl.devTogether.security.TokenProvider;

import java.io.IOException;
import java.util.List;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(final @NotNull HttpSecurity http) throws Exception {
        http

                .httpBasic(HttpBasicConfigurer::disable) //HTTP 기본 인증을 비활성화

                .csrf(CsrfConfigurer::disable)   //CSRF(Cross-Site Request Forgery) 보호를 비활성화  : 한 사이트의 사용자가 의도치 않게 다른 사이트에 요청을 보내는 공격을 의미

                .cors(c -> {
                    CorsConfigurationSource source = request -> {

                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(
                                List.of("*")
                        );
                        config.setAllowedMethods(
                                List.of("*")
                        );
                        return config;
                    };
                    c.configurationSource(source);
                })        //CORS는 다른 출처에서 리소스에 접근할 수 있는 권한을 부여하는 메커니즘

                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //세션 관리 설정을 구성하여 세션을 상태를 가지지 않는(Stateless) 방식으로 생성하도록 지정
                //세션을 상태를 가지지 않는(Stateless) 방식으로 사용하는 이유        공부하기
                //1. 확장성
                //2. 보안
                //3. 클라이언트 호환성
                //4. 캐싱
                //5. RESTful API



                .authorizeHttpRequests(authorize ->
                        authorize
                                //공통
                                .requestMatchers(  "/register","/login" , "/main", "register/email", "pwd/email", "/pwd/change","/scrap/mentee","/scrap/mentor",
                                        "/email" ,"token/change", "/token/refresh", "/nickname",
                                        "/faq", "/board", "/board/post", "/board/like", "/board/scrap","/message", "/notice","/notice/detail", "/ask/**","/mentor").permitAll()

                               // .requestMatchers("/delivery/**").authenticated()  /delivery로 시작하는 url에 대해 인증된 사용자만 접근
                               // 관리자
                               //requestMatchers( "/notice","/ask").hasRole("ADMIN")

                                .anyRequest().authenticated()
                )

                .addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)   //특정 필터를 추가하여 사용자가 제공한 토큰을 사용한 인증을 수행

                .exceptionHandling(authenticationManager -> authenticationManager
                        .authenticationEntryPoint(new AuthenticationEntryPoint() { //인증되지 않은 경우 예외처리
                            @Override
                            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

                                response.setStatus(401);
                                response.setCharacterEncoding("utf-8");
                                response.setContentType("text/html; charset=UTF-8");
                                response.getWriter().write("인증되지 않은 사용자입니다");
                            }
                        })


                        .accessDeniedHandler(new AccessDeniedHandler() { //접근 거부 예외처리
                            @Override
                            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

                                response.setStatus(403);
                                response.setCharacterEncoding("utf-8");
                                response.setContentType("text/html; charset=UTF-8");
                                response.getWriter().write("권한이 없는 사용자입니다");
                            }
                        })
                )
        ;
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}