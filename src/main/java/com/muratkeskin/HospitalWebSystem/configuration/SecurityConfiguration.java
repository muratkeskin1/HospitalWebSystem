package com.muratkeskin.HospitalWebSystem.configuration;

import com.muratkeskin.HospitalWebSystem.model.Hospital;
import com.muratkeskin.HospitalWebSystem.service.HospitalService.HospitalDetailService;
import com.muratkeskin.HospitalWebSystem.service.HospitalService.HospitalServiceImpl;
import com.muratkeskin.HospitalWebSystem.service.HospitalService.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AndRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/*
@Configuration
@EnableWebSecurity
//@ComponentScan("com.muratkeskin.HospitalWebSystem.configuration")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
   @Bean
   public CustomAuthProvider authProvider() {
       return new CustomAuthProvider();
   }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
        //super.configure(auth);
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.formLogin()
                .loginPage("/login.html")
                .and().httpBasic();
        System.out.println("security");
        http
                .authorizeRequests()
                .anyRequest().authenticated().and()
                .formLogin()
                .loginProcessingUrl("/loginpost/process")
                    .loginPage("/login").usernameParameter("hospitalName").passwordParameter("hospitalPassword")
                    .permitAll().and()
                .logout()
                    .logoutUrl("/logout").invalidateHttpSession(true).logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                    .deleteCookies("hospital","JSESSIONID").clearAuthentication(true).and().httpBasic();
    }
    @Bean
    public static ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
     @Bean
    UserDetailsService userDetailsService(){
        return  new HospitalDetailService(this.hospitalService);
    }
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.hospitalService);
        return daoAuthenticationProvider;

    }
}*/
