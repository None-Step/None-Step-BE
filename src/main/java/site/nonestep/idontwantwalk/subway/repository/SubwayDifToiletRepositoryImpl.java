package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SubwayDifToiletRepositoryImpl implements SubwayDifToiletRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;
}
