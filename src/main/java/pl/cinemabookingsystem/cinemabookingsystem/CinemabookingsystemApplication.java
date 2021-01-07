package pl.cinemabookingsystem.cinemabookingsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CinemabookingsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemabookingsystemApplication.class, args);
    }

}
