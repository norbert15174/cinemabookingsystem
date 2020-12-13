package pl.cinemabookingsystem.cinemabookingsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import pl.cinemabookingsystem.cinemabookingsystem.models.FilmShow;
import pl.cinemabookingsystem.cinemabookingsystem.models.Spectator;
import pl.cinemabookingsystem.cinemabookingsystem.services.FilmShowService;

@RestController
@RequestMapping(path = "/filmshow")
public class FilmShowRestController {

    private FilmShowService filmShowService;
    @Autowired
    public FilmShowRestController(FilmShowService filmShowService) {
        this.filmShowService = filmShowService;
    }

    @PostMapping("/addfilmshow")
    public ResponseEntity<FilmShow> addNewFilmShow(FilmShow filmShow, long roomId, long moveId){
        return filmShowService.addNewFilmShow(filmShow,roomId,moveId);
    }
    @PostMapping("/seatreservation")
    public ResponseEntity<Spectator> seatReservation(long id, Spectator spectator){
        return filmShowService.seatReservation(id,spectator);
    }

    @DeleteMapping("/deletefilmshow/{id}")
    public ResponseEntity deleteFilmShow(@PathVariable long id){
        return filmShowService.deleteFilmShow(id);
    }

    @GetMapping("confimation/{id}")
    public RedirectView confirmation(@PathVariable long id){

        return new RedirectView("https://www.baeldung.com/spring-redirect-and-forward");
    }
}
