package org.example.shoppingmall.config;

import lombok.RequiredArgsConstructor;
import org.example.shoppingmall.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                // 📌 H2 콘솔은 frame 옵션이 필요함
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                .authorizeHttpRequests(auth -> auth

                        // 📌 H2 Console 전체 허용
                        .requestMatchers("/h2-console/**").permitAll()

                        .requestMatchers("/", "/login", "/member/login", "/member/register",
                                "/css/**", "/js/**", "/img/**").permitAll()

                        // 일반 사용자 권한
                        .requestMatchers("/member/**", "/cart/**", "/order/**","/inquiry/**")
                        .hasAnyRole("USER", "ADMIN")

                        // 관리자 전용
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/member/login")          // GET: 로그인 페이지
                        .loginProcessingUrl("/member/login") // POST: 여기서 인증 처리
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/member/login?error=true")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                );
        http.userDetailsService(customUserDetailsService);

        return http.build();
    }



}
