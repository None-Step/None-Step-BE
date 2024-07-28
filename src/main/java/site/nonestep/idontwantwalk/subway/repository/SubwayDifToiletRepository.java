package site.nonestep.idontwantwalk.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.subway.entity.DifToilet;

public interface SubwayDifToiletRepository extends JpaRepository<DifToilet, Long>, SubwayDifToiletRepositoryCustom{
}
