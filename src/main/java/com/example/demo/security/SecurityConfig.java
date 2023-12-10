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
    
    /**
     * Configures the HttpSecurity object to allow access to the public
     * 
     * @param    http    An HttpSecurity object
     * @return           void
     * @see              void
    */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> 
            auth.requestMatchers(new AntPathRequestMatcher("/public/**"))
            .permitAll()
        );

        super.configure(http);
        setLoginView(http, LoginView.class);
    }

    /**
     * Overrides the configure method of the WebSecurity object
     * 
     * @param    web     An WebSecurity object
     * @return           void
     * @see              void
    */
    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    /**
     * Set the login users and their roles
     * 
     * @return           UserDetailsService
     * @see              UserDetailsService
    */
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
