package com.brigada.bloss.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.brigada.bloss.security.EntryPoint;
import com.brigada.bloss.security.RequestFilter;
import com.brigada.bloss.service.UserDetailsServiceImpl;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private EntryPoint entryPoint;

    @Bean
    public RequestFilter requestFilter() {
        return new RequestFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/enter/**").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/films/**").permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/films/**").authenticated()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.PUT, "/films/**").authenticated()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.DELETE, "/films/**").authenticated()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/reviews/**").authenticated()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.PUT, "/reviews/**").authenticated()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.DELETE, "/reviews/**").authenticated()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.GET, "/reviews/**").permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(requestFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
