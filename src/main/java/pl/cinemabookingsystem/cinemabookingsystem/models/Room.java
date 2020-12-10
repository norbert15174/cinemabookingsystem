package pl.cinemabookingsystem.cinemabookingsystem.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long numberOfSeats;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(long numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
}
