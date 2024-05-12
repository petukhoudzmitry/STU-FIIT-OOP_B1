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

/**
 * Configuration class for Spring Security setup.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig{
    /**
     * Default constructor for the SecurityConfig.
     */
    public SecurityConfig() {}

    /**
     * Autowired CustomUserDetailsService for fetching user details.
     */
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Configures the security filter chain for HTTP requests.
     *
     * @param http HttpSecurity object to configure security for HTTP requests.
     * @return Configured SecurityFilterChain object.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(CsrfConfigurer::disable).authorizeHttpRequests(
                (auth) -> auth
                        // Permitting access to certain endpoints without authentication
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/register").permitAll()
                        .requestMatchers("/auth/register/company").permitAll()
                        .requestMatchers("/auth/logout").authenticated()
                        .requestMatchers("/admin").hasAnyRole("ADMIN", "SUPER")
                        .requestMatchers("/admin/add").hasRole("SUPER")
                        .requestMatchers("/admin/block/*").hasAnyRole("ADMIN", "SUPER")
                        .requestMatchers("/admin/*").hasAnyRole("ADMIN", "SUPER")
                        .requestMatchers("/css/*").permitAll()
                        .requestMatchers("/js/*").permitAll()
                        .requestMatchers("/images/*").permitAll()
                        .requestMatchers("/petition/*").hasRole("USER")
                        .requestMatchers("/home/retract").hasRole("USER")
                        .anyRequest().authenticated())
                // Configuring form login
                .formLogin(login -> login.loginPage("/auth/login").permitAll().defaultSuccessUrl("/"))
                // Configuring logout
                .logout(logout -> {
                    logout.logoutUrl("/auth/logout");
                    logout.logoutSuccessUrl("/auth/login?logout");
                    logout.deleteCookies("JSESSIONID");
                    logout.invalidateHttpSession(true);
                })
                // Configuring authentication provider
                .authenticationProvider(daoAuthenticationProvider())
                // Configuring remember-me functionality
                .rememberMe(rememberMeConfigurer -> rememberMeConfigurer
                        .key("ultra-secret-key")
                        .tokenValiditySeconds(86400)
                        .rememberMeParameter("remember-me")
                        .userDetailsService(userDetailsService));
        return http.build();
    }

    /**
     * Creates and configures a DaoAuthenticationProvider.
     *
     * @return Configured DaoAuthenticationProvider object.
     */
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        // Setting password encoder
        daoAuthenticationProvider.setPasswordEncoder(bcrypPasswordEncoder());
        // Setting user details service
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);

        return daoAuthenticationProvider;
    }

    /**
     * Creates a BCryptPasswordEncoder bean.
     *
     * @return BCryptPasswordEncoder object.
     */
    @Bean
    PasswordEncoder bcrypPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}