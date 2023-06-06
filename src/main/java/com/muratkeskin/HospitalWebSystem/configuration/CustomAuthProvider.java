package com.muratkeskin.HospitalWebSystem.configuration;

import com.muratkeskin.HospitalWebSystem.service.HospitalService.HospitalDetailService;
import com.muratkeskin.HospitalWebSystem.service.HospitalService.HospitalServiceImpl;
import com.muratkeskin.HospitalWebSystem.service.HospitalService.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Component
@NoArgsConstructor
@AllArgsConstructor
@EnableWebSecurity
@Configuration
public class CustomAuthProvider extends WebSecurityConfigurerAdapter implements AuthenticationProvider {
    private HospitalServiceImpl hospitalService=new HospitalServiceImpl();
    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        System.out.println("auth bean");
        return  super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        AuthenticationEntryPoint entryPoint = new CustomAuthenticationEntryPoint();
        System.out.println("security2");
        http.csrf().disable();
        http
                .authorizeRequests()
                .antMatchers("/login", "/loginpost/**","/result","/logout","/login?logout")
                .permitAll()
                .antMatchers("/applies/**").fullyAuthenticated()
                .anyRequest().authenticated().and()
                .formLogin()
                .loginPage("/login").usernameParameter("hospitalEmail")
                .passwordParameter("hospitalPassword").defaultSuccessUrl("/applies").failureUrl("/login-error")
                .permitAll().and().rememberMe().disable()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login").deleteCookies("JSESSION").clearAuthentication(true)
                .invalidateHttpSession(true).and().httpBasic().authenticationEntryPoint(entryPoint);
    }
    @Autowired
    private LogoutSuccessHandler logoutSuccessHandler;
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(this);
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("static/**");
    }
    @Bean
    public UserDetailsService hospitalUserDetails() {
        return new HospitalDetailService();
    }
    @SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final  String name=authentication.getName();
        final String pw=authentication.getCredentials().toString();
        System.out.println(name+" auth provider");
        System.out.println(pw+" authprovider");
        if(hospitalService.login(name,pw)){
            final List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
            final UserDetails princ=new User(name, pw, grantedAuths);
            final Authentication auth = new UsernamePasswordAuthenticationToken(princ,pw, grantedAuths);
            return auth;
        }
        else{
            throw new AuthenticationException("hatalı giriş") {
                @Override
                public String getMessage() {
                    return "hatalı bilgi girişi";
                }
            };
        }
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
