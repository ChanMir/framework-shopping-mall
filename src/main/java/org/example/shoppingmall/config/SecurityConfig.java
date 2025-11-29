package org.example.shoppingmall.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // CSRF 끄기
                .headers(headers -> headers.frameOptions(frame -> frame.disable())) // 🔥 H2 콘솔용
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/member/login", "/member/register",
                                "/css/**", "/js/**", "/images/**",
                                "/h2-console/**").permitAll()   // 🔥 H2 콘솔 허용
                        .anyRequest().permitAll()
                )
                .formLogin(login -> login
                        .loginPage("/member/login")
                        .loginProcessingUrl("/member/login")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/member/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }
}
