package com.example.cryptoservice.security;


import com.example.cryptoservice.security.filter.JwtAuthenticationFilter;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
@RequiredArgsConstructor
public class SpringSecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**"))
                .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**"));
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/security", "POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/security/registration", "POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/crypto-users/get-all", "GET")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/crypto-users/delete", "DELETE")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/accounts/all", "GET")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/accounts/delete", "DELETE")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/transactions/get-all", "GET")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/transactions/all-fees", "GET")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/crypto-rates/add-rate", "POST")).hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}