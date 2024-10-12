package site.nonestep.idontwantwalk.board.service;


import site.nonestep.idontwantwalk.board.dto.*;

import java.util.List;

public interface BoardService {

    //공지게시글 작성
    BoardWriteResponseDTO write(BoardWriteRequestDTO boardWriteRequestDTO, Long memberNo); //회원인지 확인 토큰

    //전체 조회
    List<BoardListResponseDTO> boardList(BoardListRequestDTO boardListRequestDTO);

    //게시글 수정
    BoardModifyResponseDTO boardModify(BoardModifyRequestDTO boardModifyRequestDTO, Long memberNo);

    //게시글 삭제
    BoardDeleteResponseDTO boardDelete(BoardDeleteRequestDTO boardDeleteRequestDTO, Long memberNo);

    //게시글 상세조회
    BoardDetailResponseDTO boardDetail(BoardDetailRequestDTO boardDetailRequestDTO);

    //게시글이 몇 페이지까지 있는지
    BoardPageResponseDTO selectBoardPage();

    //글조회 최상단
    BoardMainNoticeResponseDTO boardMainNotice();

    //게시글 검색
    List<BoardSearchResponseDTO>boardSearch(BoardSearchRequestDTO boardSearchRequestDTO);
}
