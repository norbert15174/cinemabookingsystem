package pl.cinemabookingsystem.cinemabookingsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.cinemabookingsystem.cinemabookingsystem.Repository.FilmShowRepository;
import pl.cinemabookingsystem.cinemabookingsystem.models.FilmShow;
import pl.cinemabookingsystem.cinemabookingsystem.models.Movie;
import pl.cinemabookingsystem.cinemabookingsystem.models.Room;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class FilmShowService {

    private FilmShowRepository filmShowRepository;
    private RoomService roomService;
    private MovieService movieService;
    @Autowired
    public FilmShowService(FilmShowRepository filmShowRepository, RoomService roomService, MovieService movieService) {
        this.filmShowRepository = filmShowRepository;
        this.roomService = roomService;
        this.movieService = movieService;
    }




    public ResponseEntity<FilmShow> addNewFilmShow(FilmShow filmShow, long roomId, long moveId){

        Movie movie = movieService.findMovieById(moveId);
        Room room = roomService.findRoomById(roomId).getBody();
        if(movie == null || room == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        String[] time = movie.getRuntime().split(" ", 2);
        filmShow.setDateEnd(filmShow.getDateStart().plusMinutes(Integer.parseInt(time[0])+20));
        if(filmShowRepository.findFilmShow(filmShow.getDateStart(),filmShow.getDateEnd(),roomId).isPresent()) return new ResponseEntity<>(HttpStatus.FOUND);
        filmShow.setMovie(movie);
        filmShow.setRoom(room);
        try {
            return new ResponseEntity<>(filmShowRepository.save(filmShow),HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void init(){
//        FilmShow filmShow = new FilmShow();
//        filmShow.setDateStart(LocalDateTime.now().plusDays(20).withNano(0));
//        addNewFilmShow(filmShow,1L,1L);
//    }

}
