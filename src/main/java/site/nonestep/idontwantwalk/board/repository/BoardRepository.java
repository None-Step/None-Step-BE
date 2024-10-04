package site.nonestep.idontwantwalk.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board,Long>, BoardRepositoryCustom {
}
