package site.nonestep.idontwantwalk.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.subway.entity.Info;

public interface SubwayInfoRepository extends JpaRepository<Info, Long>, SubwayInfoRepositoryCustom{
}
