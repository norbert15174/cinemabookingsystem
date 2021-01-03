package pl.cinemabookingsystem.cinemabookingsystem.configurations;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pl.cinemabookingsystem.cinemabookingsystem.JWT.JwtFilter;
import pl.cinemabookingsystem.cinemabookingsystem.Repository.CinemaUserRepository;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    public WebSecurityConfig(CinemaUserRepository cinemaUserRepository, PasswordEncoderConfiguration passwordEncoderConfiguration) {
        this.cinemaUserRepository = cinemaUserRepository;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
    }

    private CinemaUserRepository cinemaUserRepository;
    private PasswordEncoderConfiguration passwordEncoderConfiguration;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/auth/**")
                .antMatchers("/filmshow/**")
                .antMatchers("/room/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests()
                .antMatchers("/movies/**").hasRole("ADMIN")
                .and()
                .addFilterBefore(new JwtFilter(cinemaUserRepository,passwordEncoderConfiguration), UsernamePasswordAuthenticationFilter.class);
//                .antMatchers("/projects/**").hasAnyRole("ADMIN","USER")
//                .antMatchers("/posts/**").hasAnyRole("ADMIN","USER")
//                .antMatchers("/auth/**").hasAnyRole("ADMIN")
//                .antMatchers("/swagger-ui").permitAll()
//                .and()


        http.csrf().disable();
    }

}
