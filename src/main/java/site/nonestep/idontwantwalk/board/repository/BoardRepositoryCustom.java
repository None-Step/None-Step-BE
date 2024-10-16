package site.nonestep.idontwantwalk.board.repository;

import site.nonestep.idontwantwalk.board.dto.BoardListResponseDTO;
import site.nonestep.idontwantwalk.board.dto.BoardMainNoticeResponseDTO;
import site.nonestep.idontwantwalk.board.dto.BoardSearchResponseDTO;
import site.nonestep.idontwantwalk.board.entity.Board;

import java.util.List;

public interface BoardRepositoryCustom {
    //전체글 조회
    List<BoardListResponseDTO> boardList(Long page);

    //게시글 삭제
    void deleteBoard(Long boardNo);

    //게시글이 몇 페이지까지 있는지
    Long selectBoardPage();

    //글조회 최상단
    BoardMainNoticeResponseDTO notice ();

    //게시글 검색
    List<BoardSearchResponseDTO>selectBoardSearch(String keyword);
}
