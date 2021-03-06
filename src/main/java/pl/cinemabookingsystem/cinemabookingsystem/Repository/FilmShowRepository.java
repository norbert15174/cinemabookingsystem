package pl.cinemabookingsystem.cinemabookingsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.cinemabookingsystem.cinemabookingsystem.models.FilmShow;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface FilmShowRepository extends JpaRepository<FilmShow, Long> {

    @Query("select f from FilmShow f where ((f.dateStart >= :dateS and f.dateStart <= :dateE) " +
            "or (f.dateEnd >= :dateS and f.dateEnd <= :dateE)" +
            "or (f.dateStart <= :dateS and f.dateEnd >= :dateE))" +
            "and f.room.id = :id")
    Optional<List<FilmShow>> findFilmShow(@Param("dateS") LocalDateTime dateS, @Param("dateE") LocalDateTime dateE, @Param("id") long id);

    @Query("select f from FilmShow f left join fetch f.spectators where f.id = :id")
    Optional<FilmShow> findFilmShowReservation(long id);

    @Query("select f from FilmShow f left join fetch f.movie left join fetch f.room where f.id = :id")
    Optional<FilmShow> findFilmShowInformation(long id);

    @Query("select f from FilmShow f left join fetch f.movie left join fetch f.room where f.dateStart > :localDate order by f.dateStart ASC ")
    List<FilmShow> findAllFilmShow(@Param("localDate") LocalDateTime localDate);

    @Query("select f from FilmShow f left join fetch f.movie left join fetch f.room where f.dateStart >= :from and f.dateEnd < :to and f.movie.title like %:title% order by f.dateStart ASC ")
    Optional<List<FilmShow>> findFilmShowByParameters(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to, @Param("title") String title);


}
