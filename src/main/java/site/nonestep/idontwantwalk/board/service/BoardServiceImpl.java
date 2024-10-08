package site.nonestep.idontwantwalk.board.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.board.dto.*;
import site.nonestep.idontwantwalk.board.entity.Board;
import site.nonestep.idontwantwalk.board.repository.BoardRepository;
import site.nonestep.idontwantwalk.member.entity.Member;
import site.nonestep.idontwantwalk.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional

public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    //공지게시글 작성
    @Override
    public BoardWriteResponseDTO write(BoardWriteRequestDTO boardWriteRequestDTO, Long memberNo) {
        Member member = memberRepository.getReferenceById(memberNo);//member pk 가지고 올게? . autowired도 해주기

        Board write = boardRepository.save(
                Board.builder()
                        .boardTitle(boardWriteRequestDTO.getBoardTitle())
                        .boardContent(boardWriteRequestDTO.getBoardContent())
                        .boardWriteDate(LocalDateTime.now())
                        .memberNo(member)
                        .build()
        );

        BoardWriteResponseDTO boardWriteResponseDTO = new BoardWriteResponseDTO();
        boardWriteResponseDTO.setBoardNo(write.getBoardNo());
        boardWriteResponseDTO.setBoardWriteDate(write.getBoardWriteDate());

        return boardWriteResponseDTO;
    }

    //전체 조회
    @Override
    public List<BoardListResponseDTO> boardList(BoardListRequestDTO boardListRequestDTO) {
        List<BoardListResponseDTO> boardList = boardRepository.boardList(boardListRequestDTO.getPage());
//        boardList = boardList.stream().map(this::boardList).toList();

        return boardList;
    }

    //게시글 수정
    @Override
    public BoardModifyResponseDTO boardModify(BoardModifyRequestDTO boardModifyRequestDTO, Long boardNo) {

        Board board = boardRepository.getReferenceById(boardModifyRequestDTO.getBoardNo());

        //게시글 수정한다. entity에서 수정한 거 그대로 가져오기
        board.modifyBoard(boardModifyRequestDTO.getBoardTitle(), boardModifyRequestDTO.getBoardContent());


        BoardModifyResponseDTO boardModifyResponseDTO = new BoardModifyResponseDTO();
        boardModifyResponseDTO.setBoardWriteDate(board.getBoardWriteDate());
        boardModifyResponseDTO.setBoardModifyDate(board.getBoardModifyDate());
        return boardModifyResponseDTO;

    }


}
