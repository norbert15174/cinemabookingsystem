package pl.cinemabookingsystem.cinemabookingsystem.configurations;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/auth/register")
//                .antMatchers("/auth/login");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests().antMatchers("/**").permitAll();
//        http.authorizeRequests()
//                .antMatchers("/persondata/**").hasAnyRole("ADMIN","USER")
//                .antMatchers("/projects/**").hasAnyRole("ADMIN","USER")
//                .antMatchers("/posts/**").hasAnyRole("ADMIN","USER")
//                .antMatchers("/auth/**").hasAnyRole("ADMIN")
//                .antMatchers("/swagger-ui").permitAll()
//                .and()
//                .addFilterBefore(new JwtFilter(userAppService), UsernamePasswordAuthenticationFilter.class);

        http.csrf().disable();
    }

}
