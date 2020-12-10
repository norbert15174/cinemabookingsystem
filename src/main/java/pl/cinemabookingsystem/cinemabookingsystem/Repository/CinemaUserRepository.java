package pl.cinemabookingsystem.cinemabookingsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cinemabookingsystem.cinemabookingsystem.models.CinemaUser;

@Repository
public interface CinemaUserRepository extends JpaRepository<CinemaUser,Long> {
}
