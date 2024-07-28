package site.nonestep.idontwantwalk.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.subway.entity.Aed;

public interface SubwayAedRepository extends JpaRepository<Aed, Long>, SubwayAedRepositoryCustom{
}
