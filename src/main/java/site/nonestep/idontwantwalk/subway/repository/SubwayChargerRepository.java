package site.nonestep.idontwantwalk.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.subway.entity.Charger;

public interface SubwayChargerRepository extends JpaRepository<Charger, Long>, SubwayChargerRepositoryCustom{
}
