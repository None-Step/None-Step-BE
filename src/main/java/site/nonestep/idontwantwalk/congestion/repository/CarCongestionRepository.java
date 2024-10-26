package site.nonestep.idontwantwalk.congestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.congestion.entity.CarCongestion;

public interface CarCongestionRepository extends JpaRepository<CarCongestion,Long>, CarCongestionRepositoryCustom {
}
