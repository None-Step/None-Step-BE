package site.nonestep.idontwantwalk.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.chat.entity.Chat;

public interface ChatRepository extends JpaRepository<Chat,Long>, ChatRepositoryCustom {
}
