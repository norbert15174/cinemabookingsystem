package pl.cinemabookingsystem.cinemabookingsystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.cinemabookingsystem.cinemabookingsystem.Repository.FilmShowRepository;
import pl.cinemabookingsystem.cinemabookingsystem.Repository.SpectatorRepository;
import pl.cinemabookingsystem.cinemabookingsystem.models.*;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FilmShowService {
    @Value("${confirmation}")
    private String confirmation;
    private FilmShowRepository filmShowRepository;
    private RoomService roomService;
    private MovieService movieService;
    private SpectatorRepository spectatorRepository;
    private MailSenderService mailSenderService;
    @Autowired
    public FilmShowService(FilmShowRepository filmShowRepository, RoomService roomService, MovieService movieService, SpectatorRepository spectatorRepository, MailSenderService mailSenderService) {
        this.filmShowRepository = filmShowRepository;
        this.roomService = roomService;
        this.movieService = movieService;
        this.spectatorRepository = spectatorRepository;
        this.mailSenderService = mailSenderService;
    }

    public ResponseEntity<List<FilmShow>> findAllFilmShow(){
        List<FilmShow> asd = filmShowRepository.findAllFilmShow(LocalDateTime.now());
        asd.forEach(a -> System.out.println(a.getId()));
        return new ResponseEntity<>(asd,HttpStatus.OK);
    }



    public ResponseEntity<FilmShow> addNewFilmShow(FilmShow filmShow, long roomId, String title){

        Movie movie = movieService.findMovieInDB(title).getBody();
        Room room = roomService.findRoomById(roomId).getBody();
        if(movie == null || room == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        String[] time = movie.getRuntime().split(" ", 2);
        filmShow.setDateEnd(filmShow.getDateStart().withSecond(0).plusMinutes(Integer.parseInt(time[0])+20));
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

    public ResponseEntity deleteFilmShow(long id){
        filmShowRepository.findFilmShowReservation(id).ifPresent(filmShow -> filmShowRepository.deleteById(filmShow.getId()));
        if(filmShowRepository.findFilmShowReservation(id).isPresent()) return new ResponseEntity(HttpStatus.ACCEPTED);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<Spectator> seatReservation(long id,Spectator spectator){
        Optional<FilmShow> filmShow = filmShowRepository.findFilmShowReservation(id);
        if(filmShow.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        if(filmShow.get().getSpectators().stream().anyMatch(spect -> spect.getSeat() == spectator.getSeat())) return new ResponseEntity<>(HttpStatus.CONFLICT);
        filmShow.get().addNewSpectator(spectator);
        spectator.setFilmShow(filmShow.get());
        long spectatorId = spectatorRepository.save(spectator).getId();
        filmShowRepository.save(filmShow.get());
        String order = "Order confirmation " + spectatorId;
        try {
            mailSenderService.sendMail(spectator.getEmail(),order,"<h2> twój kod to:" + spectatorId + "</h2>",true);
            return new ResponseEntity<>(spectator,HttpStatus.OK);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(spectator,HttpStatus.NOT_ACCEPTABLE);
        }


    }

    public void setConfirmation(long id){
        Spectator spectator = spectatorRepository.findSpectatorById(id).get();
        spectator.setFilmShow(filmShowRepository.findFilmShowInformation(spectator.getFilmShow().getId()).get());
        spectator.setBook(true);
        spectatorRepository.save(spectator);
        String content = "<h2>Your Reservation<h2><br>" +
                "<h3>Email:"+spectator.getEmail() +" <h3>" +
                "<h3>Imie:"+spectator.getName() +" <h3>" +
                "<h3>Nazwisko:"+spectator.getSurname() +" <h3>" +
                "<h3>Siedzenie:"+spectator.getSeat() +" <h3>" +
                "<h3>Sala:"+spectator.getFilmShow().getRoom().getId() +" <h3>" +
                "<h3>Film:"+spectator.getFilmShow().getMovie().getTitle() +" <h3>" +
                "<h3>Godzina:"+spectator.getFilmShow().getDateStart() +" <h3>";
        try {
            mailSenderService.sendMail(spectator.getEmail(),"Reservation" , content , true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public ResponseEntity<List<SpectatorDTO>> findSpectatorByEmail(String email) {
        Optional<List<Spectator>> spectators = spectatorRepository.findSpectatorByEmail(email);
        if(spectators.isPresent()) return new ResponseEntity(mapToSpectatorDto(spectators.get()),HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public List<SpectatorDTO> mapToSpectatorDto(List<Spectator> spectators){
        List<SpectatorDTO> spectatorDTOS = new ArrayList<>();
        spectators.forEach(spectator -> {
            SpectatorDTO spectatorDTO = new SpectatorDTO();
            spectatorDTO.setEmail(spectator.getEmail());
            spectatorDTO.setMovie(spectator.getFilmShow().getMovie().getTitle());
            spectatorDTO.setName(spectator.getName());
            spectatorDTO.setSurname(spectator.getSurname());
            spectatorDTO.setRoom(spectator.getFilmShow().getRoom().getId());
            spectatorDTO.setSeat(spectator.getSeat());
            spectatorDTO.setStartDate(spectator.getFilmShow().getDateStart());
            spectatorDTOS.add(spectatorDTO);
        });
        return spectatorDTOS;
    }

    public ResponseEntity<List<FilmShow>> findFilmShowByParametr(String from, String to, String title) {
        LocalDate dateFrom = from.isBlank() ? LocalDate.now().minusMonths(2) : LocalDate.parse(from);
        LocalDate dateTo = to.isBlank() ? LocalDate.now().plusMonths(2) : LocalDate.parse(to);
        LocalDateTime localDateTimeFrom = LocalDateTime.of(dateFrom.getYear(),dateFrom.getMonth(),dateFrom.getDayOfMonth(),0,0,0,0);
        LocalDateTime localDateTimeTo = LocalDateTime.of(dateTo.getYear(),dateTo.getMonth(),dateTo.getDayOfMonth(),0,0,0,0);
        return new ResponseEntity(filmShowRepository.findFilmShowByParameters(localDateTimeFrom,localDateTimeTo,title),HttpStatus.OK);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void init(){
////        setConfirmation(1L);
//        FilmShow filmShow = new FilmShow();
//        filmShow.setDateStart(LocalDateTime.now().plusDays(22).withNano(0));
//        addNewFilmShow(filmShow,1L,3L);
//////
//////        Spectator spectator = new Spectator();
//////        spectator.setEmail("faronnorbertkrk@gmail.com");
//////        spectator.setLocalTime(LocalTime.now().withNano(0));
//////        spectator.setName("Norbert");
//////        spectator.setSurname("Faron222");
//////        spectator.setSeat(33L);
//////        seatReservation(1L,spectator);
//////
//////
//////
//    }

}
