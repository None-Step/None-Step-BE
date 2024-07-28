package site.nonestep.idontwantwalk.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.subway.entity.Lift;

public interface SubwayLiftRepository extends JpaRepository<Lift, Long>, SubwayLiftRepositoryCustom{
}
