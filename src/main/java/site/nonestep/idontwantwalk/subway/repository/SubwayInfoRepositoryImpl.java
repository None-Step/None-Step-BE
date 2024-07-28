package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SubwayInfoRepositoryImpl implements SubwayInfoRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;
}
