package com.gmail.dedmikash.market.web.security.config;

import com.gmail.dedmikash.market.web.security.handler.AppUrlAuthenticationSuccessHandler;
import com.gmail.dedmikash.market.web.security.handler.LoginAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static com.gmail.dedmikash.market.web.constant.RolesConstants.ADMIN;
import static com.gmail.dedmikash.market.web.constant.RolesConstants.CUSTOMER;
import static com.gmail.dedmikash.market.web.constant.RolesConstants.SALE;

@Configuration
@Order(2)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder encoder;

    public WebSecurityConfigurer(UserDetailsService userDetailsService,
                                 PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/users/**", "/reviews/**")
                .hasAuthority(ADMIN)
                .antMatchers("/articles", "/articles/{\\d+}/comments", "/profile")
                .hasAnyAuthority(CUSTOMER, SALE)
                .antMatchers("/articles/{\\d+}/comments/new")
                .hasAuthority(CUSTOMER)
                .antMatchers("/articles/{\\d+}/delete", "/articles/new",
                        "/articles/{\\d+}/comments/change_article", "articles/{\\d+}/comments/delete",
                        "/items", "/items/{\\d+}")
                .hasAuthority(SALE)
                .antMatchers("/403", "/", "/login", "/login?hasNoRole=1")
                .permitAll()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(authenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(loginAccessDeniedHandler())
                .and()
                .csrf()
                .disable();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AppUrlAuthenticationSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler loginAccessDeniedHandler() {
        return new LoginAccessDeniedHandler();
    }
}
