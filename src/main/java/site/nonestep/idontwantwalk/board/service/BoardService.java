package site.nonestep.idontwantwalk.board.service;


import site.nonestep.idontwantwalk.board.dto.*;

import java.util.List;

public interface BoardService {

    //공지게시글 작성
    BoardWriteResponseDTO write(BoardWriteRequestDTO boardWriteRequestDTO, Long memberNo); //회원인지 확인 토큰

    //전체 조회
    List<BoardListResponseDTO> boardList(BoardListRequestDTO boardListRequestDTO);

    //게시글 수정
    BoardModifyResponseDTO boardModify(BoardModifyRequestDTO boardModifyRequestDTO, Long boardNo);
}
