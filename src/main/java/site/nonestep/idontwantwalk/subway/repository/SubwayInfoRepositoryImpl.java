package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.subway.dto.SubwayLocationResponseDTO;
import site.nonestep.idontwantwalk.subway.entity.Info;

import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static site.nonestep.idontwantwalk.subway.entity.QInfo.*;

public class SubwayInfoRepositoryImpl implements SubwayInfoRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public List<SubwayLocationResponseDTO> selectInfoLatitudeAndInfoLongitudeAndRadius(BigDecimal latitude, BigDecimal longitude, Long radius) {

        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(info.infoLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(info.infoLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(info.infoLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return queryFactory.select(Projections.constructor(SubwayLocationResponseDTO.class, info.region, info.line, info.station,
                info.infoAddress, info.infoLatitude, info.infoLongitude, Expressions.as(distanceExpression, distancePath)))
                .from(info)
                .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                .fetch();
    }
}
