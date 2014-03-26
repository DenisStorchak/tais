package ua.org.tees.yarosh.tais.ui.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static ua.org.tees.yarosh.tais.core.common.dto.Role.*;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/#!teacher/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/#!auth").permitAll()
                .and()
                .logout().permitAll();

//        http.authorizeRequests().anyRequest().hasAnyRole(ADMIN.toString(), TEACHER.toString(), STUDENT.toString());
//        http.antMatcher("admin/**").authorizeRequests().antMatchers("admin/**").hasRole(ADMIN.toString());
//        http.antMatcher("teacher/**").authorizeRequests().antMatchers("teacher/**").hasRole(TEACHER.toString());
//        http.antMatcher("student/**").authorizeRequests().antMatchers("student/**").hasRole(STUDENT.toString());
//        http.antMatcher("auth/**").authorizeRequests().antMatchers("auth/**").permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password("admin").roles(ADMIN.toString());
        auth.inMemoryAuthentication().withUser("teacher").password("teacher").roles(TEACHER.toString());
        auth.inMemoryAuthentication().withUser("student").password("student").roles(STUDENT.toString());
    }
}
