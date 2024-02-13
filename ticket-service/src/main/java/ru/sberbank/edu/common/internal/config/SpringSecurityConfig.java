package ru.sberbank.edu.common.internal.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            //for testing on H2 only
            .csrf(csrf -> csrf
                    .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
            .headers(headers -> headers.frameOptions(FrameOptionsConfig::disable))
            //

            .authorizeHttpRequests(authorize -> authorize
                    .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .requestMatchers("/h2-console/**").permitAll()
                    .requestMatchers("/error/*").permitAll()
                    .requestMatchers("/auth/*").permitAll()
                    .requestMatchers("/admin/*").hasRole("ADMIN")
                    .requestMatchers("/manager/*").hasAnyRole("MANAGER", "ADMIN")
                    .requestMatchers("/user/*").hasAnyRole("USER", "MANAGER", "ADMIN")
                    .requestMatchers("/api/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                    .requestMatchers("/profile/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                    .requestMatchers("/tickets/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                    .requestMatchers("/dashboard/**").hasAnyRole("USER", "MANAGER", "ADMIN")
                    .anyRequest().denyAll()
            )
            .httpBasic(Customizer.withDefaults())
            .formLogin(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
