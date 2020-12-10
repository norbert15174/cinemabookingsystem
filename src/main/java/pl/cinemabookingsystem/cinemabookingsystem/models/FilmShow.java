package pl.cinemabookingsystem.cinemabookingsystem.models;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
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
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;
    @OneToMany(mappedBy = "filmShow")
    private Set<Spectator> spectators = new HashSet<>();

    public void addNewSpectator(Spectator spectator){
        this.spectators.add(spectator);
    }
    public void deleteSpectator(Spectator spectator){
        this.spectators.remove(spectator);
    }

    public long getId() {
        return id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Set<Spectator> getSpectators() {
        return spectators;
    }

    public void setSpectators(Set<Spectator> spectators) {
        this.spectators = spectators;
    }
}
