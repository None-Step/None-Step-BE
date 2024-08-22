package site.nonestep.idontwantwalk.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.subway.entity.DownTime;

public interface SubwayDownTimeRepository extends JpaRepository<DownTime, Long>, SubwayDownTimeRepositoryCustom{
}
