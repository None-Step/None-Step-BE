package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.subway.dto.SubwayEscalResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayToiletResponseDTO;

import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static com.querydsl.core.types.dsl.MathExpressions.radians;
import static site.nonestep.idontwantwalk.subway.entity.QEscal.escal;
import static site.nonestep.idontwantwalk.subway.entity.QToilet.*;

public class SubwayToiletRepositoryImpl implements SubwayToiletRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // 역 내 화장실 조회
    @Override
    public List<SubwayToiletResponseDTO> selectToilet(BigDecimal latitude, BigDecimal longitude, Long radius) {

        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(toilet.toiletLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(toilet.toiletLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(toilet.toiletLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return queryFactory.select(Projections.constructor(SubwayToiletResponseDTO.class, toilet.info.region,
                toilet.info.line, toilet.info.station, toilet.toiletExit, toilet.toiletComment,
                toilet.toiletAddress, toilet.toiletLatitude, toilet.toiletLongitude, Expressions.as(distanceExpression, distancePath)))
                .from(toilet)
                .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                .fetch();
    }
}
