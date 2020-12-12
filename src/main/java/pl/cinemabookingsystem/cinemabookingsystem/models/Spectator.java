package pl.cinemabookingsystem.cinemabookingsystem.models;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalTime;

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
    private boolean isBook = false;
    private LocalTime localTime;

    @ManyToOne
    private FilmShow filmShow;

    public LocalTime getLocalTime() {
        return localTime;
    }

    public void setLocalTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public long getId() {
        return id;
    }

    public long getSeat() {
        return seat;
    }

    public void setSeat(long seat) {
        this.seat = seat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public boolean isBook() {
        return isBook;
    }

    public void setBook(boolean book) {
        isBook = book;
    }

    public FilmShow getFilmShow() {
        return filmShow;
    }

    public void setFilmShow(FilmShow filmShow) {
        this.filmShow = filmShow;
    }
}
