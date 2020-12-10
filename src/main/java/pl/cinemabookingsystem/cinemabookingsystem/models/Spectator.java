package pl.cinemabookingsystem.cinemabookingsystem.models;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
public class Spectator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long seat;
    @Email
    private String email;
    private String name;
    private String surname;
    @ManyToOne
    private FilmShow filmShow;
}
