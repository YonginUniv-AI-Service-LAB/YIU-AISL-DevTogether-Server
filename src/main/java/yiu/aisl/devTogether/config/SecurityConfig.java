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
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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

@Configuration //Bean을 등록하기 위한 애노테이션
@EnableWebSecurity    //모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;

    @Bean

    // filterChain 메소드가 호출될 때 필수적으로 HttpSecurity 객체가 전달되어야하며, 해당 객체는 변경될 수 없음
    public SecurityFilterChain filterChain(final @NotNull HttpSecurity http) throws Exception {
        http
                // ID, Password 문자열을 Base64로 인코딩하여 전달하는 구조
                .httpBasic(HttpBasicConfigurer::disable)

                //웹 애플리케이션에 대한 CSRF 보호를 비활성화하는 역할
                // 예시
                //사용자가 은행 웹 사이트에 로그인한 상태에서, 이메일이나 메시지 등을 통해 악의적인 웹 사이트로 이동할 경우,
                // 해당 악의적인 웹 사이트는 사용자의 브라우저를 이용하여 은행 계정으로 자동으로 이체를 시도할 수 있음
                // 이를 방지하기 위해 CSRF 토큰과 같은 보안 메커니즘이 사용됨
                .csrf(CsrfConfigurer::disable)

                // CORS 설정
                //서버는 보안상의 이유로 특정 출처에서의 요청을 거부하거나,
                // 허용한 출처에 대해서만 특정한 리소스에 접근하도록 제한할 수 있음
                .cors(c -> {
                    CorsConfigurationSource source = request -> {
                        // Cors 허용 패턴
                        CorsConfiguration config = new CorsConfiguration();

                        // 모든 출처를 허용
                        config.setAllowedOrigins(
                                List.of("*")
                        );

                        // Http 메소드 허용 (GET, POST,PUT,DELETE)
                        config.setAllowedMethods(
                                List.of("*")
                        );

                        //설정한 CorsConfiguration 객체를 반환
                        return config;
                    };
                    c.configurationSource(source);
                })

                // SessionCreationPolicy.STATELESS가 무엇이냐?
                // 세션을 생성하지 않음을 나타냄
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))


                // HTTP 요청에 대한 권한 부여를 설정
                .authorizeHttpRequests(authorize ->
                        authorize
                                // 모두 승인
                                .requestMatchers("/register", "/login", "/refresh", "/token").permitAll()



                                //인증된 사용자만이 그 외의 모든 기능을 이용할 수 있음
                                .anyRequest().authenticated()
                )




                //addFilterBefore: Spring Security 필터 체인에 사용자 지정 필터를 추가하는 메서드
                //new TokenAuthenticationFilter(tokenProvider): TokenAuthenticationFilter 객체를 생성하고 생성자로는 tokenProvider라는 객체를 인자로 받음
                // UsernamePasswordAuthenticationFilter.class:TokenAuthenticationFilter를 UsernamePasswordAuthenticationFilter  앞에 추가하여
                // 먼저 토큰 기반의 인증을 시도하고, 그 후에 사용자 이름과 암호를 사용한 인증을 처리하도록 구성함
                .addFilterBefore(new TokenAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)

                // 에러 핸들링: error가 발생했을 때 그에 대한 적정한 처리 방법을 결정하고, 이를 수행하는 것을 의미
                //exceptionHandling(): 예외처리를 설정하는 메소드
                // 인증관리자(authenticationManager) 를 매개변수로 받아서 인증 진입점 authenticationEntryPoint을 설정함
                .exceptionHandling(authenticationManager -> authenticationManager
                        //authenticationEntryPoint() 인증 진입점을 설정하는 메서드로, 인증 문제가 발생했을 때 호출되는 부분을 정의
                        .authenticationEntryPoint(new AuthenticationEntryPoint() {
                            //AuthenticationEntryPoint 인터페이스의 commence() 메서드를 오버라이드하여 인증 문제가 발생했을 때 수행할 동작 정의
                            @Override
                            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
                                // 인증 문제가 발생했을 때 이부분 호출
                                response.setStatus(401);
                                response.setCharacterEncoding("utf-8");
                                response.setContentType("text/html; charset=UTF-8");
                                response.getWriter().write("인증되지 않은 사용자입니다");
                            }
                        })

                        //accessDeniedHandler():  권한 거부 처리를 설정하는 메서드

                        // new AccessDeniedHandler(): 권한 거부 처리를 위한 AccessDeniedHandler 인터페이스를 구현한 익명 클래스를 생성
                        .accessDeniedHandler(new AccessDeniedHandler() {

                            //AccessDeniedHandler 인터페이스의 handle() 메서드를 오버라이드하여, 권한 문제가 발생했을 때 수행할 동작을 정의
                            @Override
                            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
                                // 권한 문제가 발생했을 때 이부분 호출
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
    public WebSecurityCustomizer webSecurityCustomizer() {

        // ignoring()을 사용하여 특정 요청을 무시하도록 설정
        return (web) -> web.ignoring().requestMatchers(
                /* swagger v2 */
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                /* swagger v3 */
                "/v3/api-docs/**",
                "/swagger-ui/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}