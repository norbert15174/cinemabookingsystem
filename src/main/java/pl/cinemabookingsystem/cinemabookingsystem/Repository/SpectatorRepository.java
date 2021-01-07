package pl.cinemabookingsystem.cinemabookingsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.cinemabookingsystem.cinemabookingsystem.models.Spectator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SpectatorRepository extends JpaRepository<Spectator, Long> {

    @Query("select s from Spectator s left join fetch s.filmShow f left join fetch f.room left join fetch f.movie where s.id = :id")
    Optional<Spectator> findSpectatorById(@Param("id") long id);

    @Query("select s from Spectator s left join fetch s.filmShow f left join fetch f.room left join fetch f.movie where s.email = :email ")
    Optional<List<Spectator>> findSpectatorByEmail(@Param("email") String email);

    @Query("select s.id from Spectator s where s.isBook = false and s.localTime < :date")
    Optional<List<Long>> findNotBookedSpectators(@Param("date") LocalTime date);


}
