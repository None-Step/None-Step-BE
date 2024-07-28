package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class SubwayNursingRepositoryImpl implements SubwayNursingRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;
}
