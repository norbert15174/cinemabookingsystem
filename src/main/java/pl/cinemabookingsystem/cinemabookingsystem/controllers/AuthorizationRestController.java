package pl.cinemabookingsystem.cinemabookingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.cinemabookingsystem.cinemabookingsystem.services.CinemaUserService;

import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthorizationRestController {

    private CinemaUserService cinemaUserService;
    @Autowired
    public AuthorizationRestController(CinemaUserService cinemaUserService) {
        this.cinemaUserService = cinemaUserService;
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String,String> login) {
        String username = login.get("username");
        String password = login.get("password");
        if (username.isBlank() || password.isBlank()) return new ResponseEntity(HttpStatus.BAD_REQUEST);
        String token = cinemaUserService.login(username, password);
        if (token == null) return new ResponseEntity<>("The username or password is incorrect", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
