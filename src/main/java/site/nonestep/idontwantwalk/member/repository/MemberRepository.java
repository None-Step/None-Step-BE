package site.nonestep.idontwantwalk.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long>, MemberRepositoryCustom {
}
