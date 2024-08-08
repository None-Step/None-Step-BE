package site.nonestep.idontwantwalk.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.subway.entity.Escal;

public interface SubwayEscalRepository extends JpaRepository<Escal, Long>, SubwayEscalRepositoryCustom{
}