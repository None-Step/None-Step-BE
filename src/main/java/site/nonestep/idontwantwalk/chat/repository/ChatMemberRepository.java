package site.nonestep.idontwantwalk.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.chat.entity.ChatMember;

public interface ChatMemberRepository extends JpaRepository<ChatMember,Long>, ChatMemberRepositoryCustom{
}
