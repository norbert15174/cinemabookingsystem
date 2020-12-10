package pl.cinemabookingsystem.cinemabookingsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pl.cinemabookingsystem.cinemabookingsystem.models.Room;
@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
}
