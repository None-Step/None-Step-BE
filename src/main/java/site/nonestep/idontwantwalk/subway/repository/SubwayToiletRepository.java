package site.nonestep.idontwantwalk.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.subway.entity.Toilet;

public interface SubwayToiletRepository extends JpaRepository<Toilet, Long>, SubwayToiletRepositoryCustom{
}
