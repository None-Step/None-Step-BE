package site.nonestep.idontwantwalk.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.subway.entity.Center;

public interface SubwayCenterRepository extends JpaRepository<Center, Long>, SubwayCenterRepositoryCustom{
}
