package pl.cinemabookingsystem.cinemabookingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.cinemabookingsystem.cinemabookingsystem.models.Movie;
import pl.cinemabookingsystem.cinemabookingsystem.services.MovieService;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/movies")
public class MovieRestController {
    MovieService movieService;
    @Autowired
    public MovieRestController(MovieService movieService) {
        this.movieService = movieService;
    }
    @GetMapping("/find/{title}")
    public ResponseEntity<Movie> findNewFilm(@PathVariable String title){
        return movieService.findNewFilm(title);
    }
    @GetMapping("/add/{title}")
    public ResponseEntity<Movie> addNewFilm(@PathVariable String title){
        return movieService.addNewFilm(title);
    }

    @GetMapping("/findindb/{title}")
    public ResponseEntity<Movie> findMovieInDB(@PathVariable String title){
        return movieService.findMovieInDB(title);
    }

    @GetMapping
    public ResponseEntity<List<Movie>> findAllMovie(){
        return movieService.findAllMovies();
    }

}
