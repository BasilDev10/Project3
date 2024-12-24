package com.example.project3.Config;

import com.example.project3.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigurationSecurity {

    private final MyUserDetailsService myUserDetailsService;


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/customer/register-customer" ,"/api/v1/employee/register-employee").permitAll()
                .requestMatchers("/api/v1/customer/get-customer-profile" , "/api/v1/customer/update" ,
                        "/api/v1/account/get-by-id/{accountId}" ,
                        "/api/v1/account/get-my-account" ,
                        "/api/v1/account/deposit/{accountId}/{amount}",
                        "/api/v1/account/withdraw/{accountId}/{amount}",
                        "/api/v1/account/transfer").hasAuthority("CUSTOMER")
                .requestMatchers("/api/v1/employee/get-employee-profile", "/api/v1/employee/update","/api/v1/account/get-all",
                        "/api/v1/account/activate/{accountId}",
                        "/api/v1/account/deactivate/{accountId}").hasAuthority("EMPLOYEE")
                .anyRequest().hasAuthority("ADMIN")
                .and()
                .logout().logoutUrl("/api/v1/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();

        //.requestMatchers().hasAuthority("USER")
    }
}