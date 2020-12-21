package pl.cinemabookingsystem.cinemabookingsystem.models;

import java.time.LocalDateTime;

public class SpectatorDTO {
    private long seat;
    private String name;
    private String surname;
    private String email;
    private long room;
    private String movie;
    private LocalDateTime startDate;

    public SpectatorDTO() {
    }

    public long getSeat() {
        return seat;
    }

    public void setSeat(long seat) {
        this.seat = seat;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getRoom() {
        return room;
    }

    public void setRoom(long room) {
        this.room = room;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }
}
