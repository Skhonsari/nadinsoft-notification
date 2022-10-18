package com.nadinsoft.interview.config.security;


import com.nadinsoft.interview.model.User;
import com.nadinsoft.interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void postConstruct() {
        User admin = userRepository.findByUsername("admin");
        if (admin == null) {
            admin = new User("admin", "admin");
        }
        admin.setPassword(passwordEncoder.encode("admin"));
        userRepository.save(admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests()
                .antMatchers("/api/login/", "swagger-ui/index.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .userDetailsService(userDetailsService)
                .formLogin()
                .successHandler((request, response, authentication) -> {})
                .loginProcessingUrl("/api/login");
        return http.build();
    }


    public static class BeansPot {
        @Bean
        public BCryptPasswordEncoder bCryptPasswordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
}
