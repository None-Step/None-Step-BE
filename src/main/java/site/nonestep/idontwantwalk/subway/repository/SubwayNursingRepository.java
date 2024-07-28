package site.nonestep.idontwantwalk.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.subway.entity.NursingRoom;

public interface SubwayNursingRepository extends JpaRepository<NursingRoom, Long>, SubwayNursingRepositoryCustom{
}
