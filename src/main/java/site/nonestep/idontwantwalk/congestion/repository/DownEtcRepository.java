package site.nonestep.idontwantwalk.congestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.congestion.entity.DownEtc;

public interface DownEtcRepository extends JpaRepository<DownEtc,Long>, DownEtcRepositoryCustom{
}
