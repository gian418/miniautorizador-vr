package br.com.albano.testevr.security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig {

    @Value("${demo.username:user}")
    private String userName;

    @Value("${demo.password:password}")
    private String password;

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user = User.builder()
                .username(userName)
                .password(passwordEncoder().encode(password))
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
        http.authorizeRequests(authz -> {
                    authz.requestMatchers("/cartoes/**").authenticated();
                    authz.requestMatchers("/transacoes/**").authenticated();
                    authz.anyRequest().permitAll();
                }).authenticationManager(authenticationManager)
                .httpBasic(withDefaults());
        http.csrf(csrf -> csrf
                .ignoringRequestMatchers("/cartoes/**")
                .ignoringRequestMatchers("/transacoes/**")
                .csrfTokenRepository(new CookieCsrfTokenRepository())).sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.NEVER));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}