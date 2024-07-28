package site.nonestep.idontwantwalk.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.subway.entity.Atm;

public interface SubwayATMRepository extends JpaRepository<Atm, Long>, SubwayATMRepositoryCustom {
}
