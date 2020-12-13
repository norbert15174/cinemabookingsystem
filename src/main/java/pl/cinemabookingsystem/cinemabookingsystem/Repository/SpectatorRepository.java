package pl.cinemabookingsystem.cinemabookingsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pl.cinemabookingsystem.cinemabookingsystem.models.Spectator;

import java.util.Optional;

@Repository
public interface SpectatorRepository extends JpaRepository<Spectator,Long> {

    @Query("select s from Spectator s left join fetch s.filmShow f left join fetch f.room left join fetch f.movie where s.id = :id")
    public Optional<Spectator> findSpectatorById(@Param("id") long id);


}
