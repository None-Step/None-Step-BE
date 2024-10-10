package site.nonestep.idontwantwalk.board.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.board.dto.BoardListResponseDTO;
import site.nonestep.idontwantwalk.board.dto.BoardMainNoticeResponseDTO;
import site.nonestep.idontwantwalk.board.entity.Board;

import java.util.List;

import static site.nonestep.idontwantwalk.board.entity.QBoard.board;

public class BoardRepositoryImpl implements BoardRepositoryCustom{
    @Autowired
    private JPAQueryFactory queryFactory;


    //게시글 조회
    @Override
    public List<BoardListResponseDTO> boardList(Long page) {
        return queryFactory.select(Projections.constructor(BoardListResponseDTO.class, board.boardNo, board.boardTitle, board.boardContent, board.boardWriteDate, board.boardModifyDate))
                .from(board)
                .orderBy(board.boardNo.desc())
                .limit(5)
                .offset(page*5)
                .fetch();
    }

    //게시판 삭제
    @Override
    public void deleteBoard(Long boardNo) {
        queryFactory.delete(board)
                .where(board.boardNo.eq(boardNo))
                .execute();
    }


    //게시글이 몇 페이지까지 있는지
    @Override
    public Long selectBoardPage() {
        return queryFactory.select(board.boardNo.count())
                .from(board)
                .fetchFirst();
    }

    //게시글조회 최상단
    @Override
    public BoardMainNoticeResponseDTO notice() {
        return queryFactory.select(Projections.constructor(BoardMainNoticeResponseDTO.class, board.boardNo, board.boardTitle))
                .from(board)
                .orderBy(board.boardNo.desc())
                .fetchFirst();
    }


}
