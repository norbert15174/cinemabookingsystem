package pl.cinemabookingsystem.cinemabookingsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cinemabookingsystem.cinemabookingsystem.models.Track;

@Repository
public interface TrackRepository extends JpaRepository<Track,Long>{
}
