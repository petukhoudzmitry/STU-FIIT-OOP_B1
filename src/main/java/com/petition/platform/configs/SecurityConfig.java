package com.petition.platform.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Autowired;
import com.petition.platform.services.CustomUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(CsrfConfigurer::disable).authorizeHttpRequests(
                (auth) -> auth
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/register").permitAll()
                        .requestMatchers("/auth/logout").authenticated()
                        .requestMatchers("/super/*").hasRole("SUPER")
                        .requestMatchers("/css/*").permitAll()
                        .requestMatchers("/js/*").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login.loginPage("/auth/login").permitAll().defaultSuccessUrl("/"))
                .logout(logout -> {
                    logout.logoutUrl("/auth/logout");
                    logout.logoutSuccessUrl("/auth/login?logout");
                    logout.deleteCookies("JSESSIONID");
                    logout.invalidateHttpSession(true);
                })
                .authenticationProvider(daoAuthenticationProvider())
                .rememberMe(rememberMeConfigurer -> rememberMeConfigurer
                        .key("ultra-secret-key")
                        .tokenValiditySeconds(86400)
                        .rememberMeParameter("remember-me")
                        .userDetailsService(userDetailsService));
        return http.build();
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        daoAuthenticationProvider.setPasswordEncoder(bcrypPasswordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return daoAuthenticationProvider;
    }

    @Bean
    PasswordEncoder bcrypPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
