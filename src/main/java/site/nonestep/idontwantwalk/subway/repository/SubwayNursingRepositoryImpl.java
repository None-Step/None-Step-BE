package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.subway.dto.SubwayDifToiletResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayNursingResponseDTO;
import site.nonestep.idontwantwalk.subway.entity.NursingRoom;

import java.math.BigDecimal;
import java.util.List;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static com.querydsl.core.types.dsl.MathExpressions.radians;
import static site.nonestep.idontwantwalk.subway.entity.QDifToilet.difToilet;
import static site.nonestep.idontwantwalk.subway.entity.QNursingRoom.*;

public class SubwayNursingRepositoryImpl implements SubwayNursingRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // 역 내 수유실 조회
    @Override
    public List<SubwayNursingResponseDTO> selectNursingRoom(BigDecimal latitude, BigDecimal longitude, Long radius) {

        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(nursingRoom.nursingLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(nursingRoom.nursingLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(nursingRoom.nursingLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return queryFactory.select(Projections.constructor(SubwayNursingResponseDTO.class, nursingRoom.info.region,
                nursingRoom.info.line, nursingRoom.info.station, nursingRoom.nursingComment, nursingRoom.nursingAddress,
                nursingRoom.nursingLatitude, nursingRoom.nursingLongitude, Expressions.as(distanceExpression, distancePath)))
                .from(nursingRoom)
                .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                .fetch();
    }
}
