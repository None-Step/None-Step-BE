package site.nonestep.idontwantwalk.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.subway.entity.Elevator;

public interface SubwayElevatorRepository extends JpaRepository<Elevator, Long>, SubwayElevatorRepositoryCustom{
}
