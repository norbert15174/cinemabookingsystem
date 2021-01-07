package pl.cinemabookingsystem.cinemabookingsystem.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.cinemabookingsystem.cinemabookingsystem.Repository.SpectatorRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Component
public class ScheduleConf {

    private SpectatorRepository spectatorRepository;
    @Autowired
    public ScheduleConf(SpectatorRepository spectatorRepository) {
        this.spectatorRepository = spectatorRepository;
    }

    @Scheduled(fixedRate = (15*60*1000))
    public void deleteNotActivatedReservations() {
        Optional<List<Long>> ids = spectatorRepository.findNotBookedSpectators(LocalTime.now().minusMinutes(15l));
        if(ids.isPresent()) {
            ids.get().forEach(id -> spectatorRepository.deleteById(id));
        }
    }

}
