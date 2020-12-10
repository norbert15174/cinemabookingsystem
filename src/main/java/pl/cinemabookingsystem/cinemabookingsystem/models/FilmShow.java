package pl.cinemabookingsystem.cinemabookingsystem.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class FilmShow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private Room room;
    @OneToOne
    private Movie movie;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    @OneToMany(mappedBy = "filmShow")
    private Set<Spectator> spectators;
}
