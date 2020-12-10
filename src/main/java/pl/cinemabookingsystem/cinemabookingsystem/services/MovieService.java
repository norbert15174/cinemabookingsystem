package pl.cinemabookingsystem.cinemabookingsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.cinemabookingsystem.cinemabookingsystem.Repository.CinemaUserRepository;
import pl.cinemabookingsystem.cinemabookingsystem.Repository.MovieRepository;
import pl.cinemabookingsystem.cinemabookingsystem.controllers.MovieController;
import pl.cinemabookingsystem.cinemabookingsystem.models.CinemaUser;
import pl.cinemabookingsystem.cinemabookingsystem.models.Movie;

import javax.persistence.Entity;

@Service
public class MovieService {
    private MovieRepository movieRepository;
    private MovieController movieController;
    @Autowired
    public MovieService(MovieRepository movieRepository, MovieController movieController) {
        this.movieRepository = movieRepository;
        this.movieController = movieController;
    }


    public ResponseEntity<Movie> findNewFilm(String title){
        Movie movie = movieController.getNewTrack(title);
        if(movie == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(movie,HttpStatus.OK);
    }

    public ResponseEntity<Movie> addNewFilm(String title){
        Movie movie = movieController.getNewTrack(title);
        if(movie == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(movieRepository.findFirstByTitle(title).isPresent()) return new ResponseEntity<>(HttpStatus.FOUND);
        movieRepository.save(movie);
        return new ResponseEntity<>(movie,HttpStatus.CREATED);
    }


}
