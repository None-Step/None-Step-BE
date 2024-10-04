package site.nonestep.idontwantwalk.board.service;


import site.nonestep.idontwantwalk.board.dto.BoardWriteRequestDTO;
import site.nonestep.idontwantwalk.board.dto.BoardWriteResponseDTO;

public interface BoardService {

    //공지게시글 작성
    BoardWriteResponseDTO write(BoardWriteRequestDTO boardWriteRequestDTO, Long memberNo); //회원인지 확인 토큰

}
