package pl.cinemabookingsystem.cinemabookingsystem.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.cinemabookingsystem.cinemabookingsystem.Repository.CinemaUserRepository;
import pl.cinemabookingsystem.cinemabookingsystem.configurations.PasswordEncoderConfiguration;
import pl.cinemabookingsystem.cinemabookingsystem.models.CinemaUser;

@Service
public class CinemaUserService implements UserDetailsService {

    private CinemaUserRepository cinemaUserRepository;
    private PasswordEncoder passwordEncoder;


    private String key = "x!A%D*G-KaPdSgVkYp3s6v8y/B?E(H+MbQeThWmZq4t7w!z$C&F)J@NcRfUjXn2r";

    @Autowired
    public CinemaUserService(CinemaUserRepository cinemaUserRepository, PasswordEncoder passwordEncoder) {
        this.cinemaUserRepository = cinemaUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return cinemaUserRepository.findAllByUsername(s);
    }

    public String login(String username, String password) {
        UserDetails userDetails = loadUserByUsername(username);
        if (userDetails == null) return null;
        if (passwordEncoder.matches(password, userDetails.getPassword()) && userDetails.isEnabled()) {
            return generateJwt(username, password);
        }
        return null;
    }

    private String generateJwt(String username, String password) {
        Algorithm algorithm = Algorithm.HMAC512(key);
        return JWT.create().withClaim("username", username).withClaim("password", password).sign(algorithm);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    void init(){
//        CinemaUser cinemaUser = new CinemaUser();
//        cinemaUser.setEmail("faronnorbertkrk@gmail.com");
//        cinemaUser.setPassword(passwordEncoder.encode("najlepszy"));
//        cinemaUser.setUsername("norbert");
//        cinemaUserRepository.save(cinemaUser);
//    }


}
