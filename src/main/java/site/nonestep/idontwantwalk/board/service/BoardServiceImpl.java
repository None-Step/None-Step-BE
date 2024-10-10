package site.nonestep.idontwantwalk.board.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import site.nonestep.idontwantwalk.board.dto.*;
import site.nonestep.idontwantwalk.board.entity.Board;
import site.nonestep.idontwantwalk.board.entity.BoardImg;
import site.nonestep.idontwantwalk.board.repository.BoardRepository;
import site.nonestep.idontwantwalk.board.repository.ImgRepository;
import site.nonestep.idontwantwalk.member.entity.Member;
import site.nonestep.idontwantwalk.member.repository.MemberRepository;
import site.nonestep.idontwantwalk.member.service.S3UploadService;

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

    @Autowired
    private ImgRepository imgRepository;

    @Autowired
    private S3UploadService s3UploadService;

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
    public BoardModifyResponseDTO boardModify(BoardModifyRequestDTO boardModifyRequestDTO, Long memberNo) {

        Board board = boardRepository.getReferenceById(boardModifyRequestDTO.getBoardNo());

        //게시글 수정한다. entity에서 수정한 거 그대로 가져오기
        board.modifyBoard(boardModifyRequestDTO.getBoardTitle(), boardModifyRequestDTO.getBoardContent());


        BoardModifyResponseDTO boardModifyResponseDTO = new BoardModifyResponseDTO();
        boardModifyResponseDTO.setBoardWriteDate(board.getBoardWriteDate());
        boardModifyResponseDTO.setBoardModifyDate(board.getBoardModifyDate());
        return boardModifyResponseDTO;

    }

    //게시글 삭제
    @Override
    public BoardDeleteResponseDTO boardDelete(BoardDeleteRequestDTO boardDeleteRequestDTO, Long memberNo) {
        Board board = boardRepository.getReferenceById(boardDeleteRequestDTO.getBoardNo());
        //Member member = memberRepository.getReferenceById(memberNo);
        // 게시판 글쓴이랑 member랑 비교해서 다른 사람이면 잘못된 접근이므로 null을 return한다. 관리자만 사용할거라면?

        boardRepository.deleteBoard(board.getBoardNo());

        BoardDeleteResponseDTO boardDeleteResponseDTO = new BoardDeleteResponseDTO();
        boardDeleteResponseDTO.setMessage("Success");

        return boardDeleteResponseDTO;
    }

    //게시글 상세조회
    @Override
    public BoardDetailResponseDTO boardDetail(BoardDetailRequestDTO boardDetailRequestDTO) {
        Board board = boardRepository.getReferenceById(boardDetailRequestDTO.getBoardNo());
        //Member member = board.getMemberNo();

        // List<String> boardImg = imgRepository.findAllBoardDetailImg(board);

        BoardDetailResponseDTO boardDetailResponseDTO = new BoardDetailResponseDTO();
        boardDetailResponseDTO.setBoardNo(board.getBoardNo());
        boardDetailResponseDTO.setBoardTitle(board.getBoardTitle());
        boardDetailResponseDTO.setBoardContent(board.getBoardContent());
        boardDetailResponseDTO.setBoardWriteDate(board.getBoardWriteDate());
        boardDetailResponseDTO.setBoardModifyDate(board.getBoardModifyDate());
        return boardDetailResponseDTO;
    }



}
