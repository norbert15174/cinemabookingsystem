package pl.cinemabookingsystem.cinemabookingsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cinemabookingsystem.cinemabookingsystem.models.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Long> {
}
