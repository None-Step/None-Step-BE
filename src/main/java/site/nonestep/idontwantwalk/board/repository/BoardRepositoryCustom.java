package site.nonestep.idontwantwalk.board.repository;

import site.nonestep.idontwantwalk.board.dto.BoardListResponseDTO;
import site.nonestep.idontwantwalk.board.entity.Board;

import java.util.List;
import java.util.Optional;

public interface BoardRepositoryCustom {
    //전체글 조회
    List<BoardListResponseDTO> boardList(Long page);

    //게시글 삭제
    void deleteBoard(Long boardNo);

    //상세조회

}
