package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.ui.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> 
            auth.requestMatchers(new AntPathRequestMatcher("/public/**"))
            .permitAll()
        );

        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails funcionario =
            User.withUsername("funcionario")
                .password("{noop}funcionario")
                .roles("FUNCIONARIO")
                .build();
        UserDetails gerente =
            User.withUsername("gerente")
                .password("{noop}gerente")
                .roles("GERENTE")
                .build();
        return new InMemoryUserDetailsManager(funcionario, gerente);
    }
}
