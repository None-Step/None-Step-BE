package site.nonestep.idontwantwalk.board.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class BoardRepositoryImpl implements BoardRepositoryCustom{
    @Autowired
    private JPAQueryFactory queryFactory;


}
