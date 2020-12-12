package pl.cinemabookingsystem.cinemabookingsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.cinemabookingsystem.cinemabookingsystem.Repository.FilmShowRepository;
import pl.cinemabookingsystem.cinemabookingsystem.Repository.SpectatorRepository;
import pl.cinemabookingsystem.cinemabookingsystem.models.FilmShow;
import pl.cinemabookingsystem.cinemabookingsystem.models.Movie;
import pl.cinemabookingsystem.cinemabookingsystem.models.Room;
import pl.cinemabookingsystem.cinemabookingsystem.models.Spectator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

@Service
public class FilmShowService {

    private FilmShowRepository filmShowRepository;
    private RoomService roomService;
    private MovieService movieService;
    private SpectatorRepository spectatorRepository;
    @Autowired
    public FilmShowService(FilmShowRepository filmShowRepository, RoomService roomService, MovieService movieService, SpectatorRepository spectatorRepository) {
        this.filmShowRepository = filmShowRepository;
        this.roomService = roomService;
        this.movieService = movieService;
        this.spectatorRepository = spectatorRepository;
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

    public ResponseEntity<Spectator> seatReservation(long id,Spectator spectator){
        Optional<FilmShow> filmShow = filmShowRepository.findFilmShowReservation(id);
        if(filmShow.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(filmShow.get().getSpectators().stream().anyMatch(spect -> spect.getSeat() == spectator.getSeat())) return new ResponseEntity<>(HttpStatus.CONFLICT);
        filmShow.get().addNewSpectator(spectator);
        spectator.setFilmShow(filmShow.get());
        spectatorRepository.save(spectator);
        filmShowRepository.save(filmShow.get());
        return new ResponseEntity<>(spectator,HttpStatus.OK);

    }





//    @EventListener(ApplicationReadyEvent.class)
//    public void init(){
////        FilmShow filmShow = new FilmShow();
////        filmShow.setDateStart(LocalDateTime.now().plusDays(20).withNano(0));
////        addNewFilmShow(filmShow,1L,1L);
//
//        Spectator spectator = new Spectator();
//        spectator.setEmail("faronnorbertkrk@gmail.com");
//        spectator.setLocalTime(LocalTime.now().withNano(0));
//        spectator.setName("Norbert");
//        spectator.setSurname("Faron222");
//        spectator.setSeat(33L);
//        seatReservation(1L,spectator);
//
//
//
//    }

}
