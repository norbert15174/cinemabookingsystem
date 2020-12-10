package pl.cinemabookingsystem.cinemabookingsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.cinemabookingsystem.cinemabookingsystem.models.Spectator;
@Repository
public interface SpectatorRepository extends JpaRepository<Spectator,Long> {
}
