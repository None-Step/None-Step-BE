package site.nonestep.idontwantwalk.congestion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.congestion.entity.UpCongestion;
import site.nonestep.idontwantwalk.member.repository.MemberRepositoryCustom;

public interface UpCongestionRepository extends JpaRepository<UpCongestion,Long>, UpCongestionRepositoryCustom {
}
