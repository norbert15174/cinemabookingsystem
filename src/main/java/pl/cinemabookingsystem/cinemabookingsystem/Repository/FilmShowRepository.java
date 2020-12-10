package pl.cinemabookingsystem.cinemabookingsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.cinemabookingsystem.cinemabookingsystem.models.FilmShow;

public interface FilmShowRepository extends JpaRepository<FilmShow,Long> {
}
