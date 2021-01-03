package pl.cinemabookingsystem.cinemabookingsystem.JWT;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import pl.cinemabookingsystem.cinemabookingsystem.Repository.CinemaUserRepository;
import pl.cinemabookingsystem.cinemabookingsystem.configurations.PasswordEncoderConfiguration;
import pl.cinemabookingsystem.cinemabookingsystem.models.CinemaUser;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtFilter extends OncePerRequestFilter {

   @Value("${Algorithm-key}")
    private String key;

    private CinemaUserRepository cinemaUserRepository;
    private PasswordEncoderConfiguration passwordEncoderConfiguration;

    public JwtFilter(CinemaUserRepository cinemaUserRepository, PasswordEncoderConfiguration passwordEncoderConfiguration) {
        this.cinemaUserRepository = cinemaUserRepository;
        this.passwordEncoderConfiguration = passwordEncoderConfiguration;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authorization = httpServletRequest.getHeader("Authorization");
        UsernamePasswordAuthenticationToken authenticationToken = getUsernamePasswordAuthenticationToken(authorization);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private UsernamePasswordAuthenticationToken getUsernamePasswordAuthenticationToken(String authorization) {
        try{
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC512("x!A%D*G-KaPdSgVkYp3s6v8y/B?E(H+MbQeThWmZq4t7w!z$C&F)J@NcRfUjXn2r")).build();
            DecodedJWT verify = jwtVerifier.verify(authorization.substring(7));
            String username = verify.getClaim("username").asString();
            String password = verify.getClaim("password").asString();
            UserDetails userDetails = cinemaUserRepository.findAllByUsername(username);
            if(userDetails == null) return null;
            if(!passwordEncoderConfiguration.getPasswordEncoder().matches(password,userDetails.getPassword())) return null;

            return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        }catch (Exception e){
            System.out.println("message : " + e.getMessage());
            return null;
        }



    }


}
