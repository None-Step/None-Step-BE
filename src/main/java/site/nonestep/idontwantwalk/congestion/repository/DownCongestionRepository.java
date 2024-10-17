package site.nonestep.idontwantwalk.congestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.congestion.entity.DownCongestion;
import site.nonestep.idontwantwalk.member.repository.MemberRepositoryCustom;

public interface DownCongestionRepository extends JpaRepository<DownCongestion,Long>, DownCongestionRepositoryCustom {
}
