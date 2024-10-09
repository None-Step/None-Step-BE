package site.nonestep.idontwantwalk.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.board.entity.BoardImg;

public interface ImgRepository  extends JpaRepository<BoardImg, Long>, ImgRepositoryCustom{
}
