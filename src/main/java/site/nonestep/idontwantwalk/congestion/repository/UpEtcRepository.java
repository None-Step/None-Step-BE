package site.nonestep.idontwantwalk.congestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.congestion.entity.UpEtc;

public interface UpEtcRepository extends JpaRepository<UpEtc,Long>, UpEtcRepositoryCustom{
}
