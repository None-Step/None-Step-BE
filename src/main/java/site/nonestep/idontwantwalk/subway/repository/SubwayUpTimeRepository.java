package site.nonestep.idontwantwalk.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.subway.entity.UpTime;

public interface SubwayUpTimeRepository extends JpaRepository<UpTime, Long>, SubwayUpTimeRepositoryCustom{
}
