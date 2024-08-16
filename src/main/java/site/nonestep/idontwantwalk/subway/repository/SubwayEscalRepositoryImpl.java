package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.road.dto.GoRoadRequestDTO;
import site.nonestep.idontwantwalk.road.dto.GoStationRequestDTO;
import site.nonestep.idontwantwalk.road.dto.SkResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.InfoEscal;
import site.nonestep.idontwantwalk.subway.dto.SubwayElevatorResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayEscalResponseDTO;
import site.nonestep.idontwantwalk.subway.entity.Escal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static com.querydsl.core.types.dsl.MathExpressions.radians;
import static site.nonestep.idontwantwalk.subway.entity.QElevator.elevator;
import static site.nonestep.idontwantwalk.subway.entity.QEscal.*;

public class SubwayEscalRepositoryImpl implements SubwayEscalRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    // 에스컬레이터 있는 역 조회
    @Override
    public List<SubwayEscalResponseDTO> selectEscal(BigDecimal latitude, BigDecimal longitude, Long radius) {
        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(escal.escalLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(escal.escalLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(escal.escalLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return queryFactory.select(Projections.constructor(SubwayEscalResponseDTO.class, escal.info.region,
                        escal.info.line, escal.info.station, escal.escalComment, escal.escalAddress,
                        escal.escalLatitude, escal.escalLongitude, Expressions.as(distanceExpression, distancePath)))
                .from(escal)
                .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                .fetch();
    }

    // 역 1개 정보 전체 조회 시 필요한 코드
    @Override
    public List<InfoEscal> selectEscal(String region, String line, String station) {
        return queryFactory.select(Projections.constructor(InfoEscal.class, escal.escalComment))
                .from(escal)
                .where(escal.info.region.eq(region).and(escal.info.line.eq(line).and(escal.info.station.eq(station))))
                .fetch();
    }

    // 지역, 호선, 역 명을 입력하면 역의 가장 가까운 에스컬레이터의 위도, 경도를 return
    @Override
    public Optional<SkResponseDTO> selectNearByEscal(GoStationRequestDTO goStationRequestDTO) {

        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(goStationRequestDTO.getCurrentLatitude())))
                .multiply(sin(radians(escal.escalLatitude)))
                .add(cos(radians(Expressions.constant(goStationRequestDTO.getCurrentLatitude())))
                        .multiply(cos(radians(escal.escalLatitude)))
                        .multiply(cos(radians(Expressions.constant(goStationRequestDTO.getCurrentLongitude())).subtract(
                                radians(escal.escalLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return Optional.ofNullable(
                queryFactory.select(Projections.constructor(SkResponseDTO.class, escal.escalLatitude,
                                escal.escalLongitude, Expressions.as(distanceExpression, distancePath)))
                        .from(escal)
                        .where(escal.info.region.eq(goStationRequestDTO.getGoRegion()).and(escal.info.station.eq(goStationRequestDTO.getGoStation())))
                        .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                        .fetchFirst()
        );
    }

    // [길 찾기: 역 > 목적지] 지역, 역 명을 넣으면 기본 역의 가장 가까운 에스컬레이터의 위도, 경도를 return 하는 것
    @Override
    public Optional<SkResponseDTO> selectGoRoadEscal(GoRoadRequestDTO goRoadRequestDTO) {

        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(goRoadRequestDTO.getGoLatitude())))
                .multiply(sin(radians(escal.escalLatitude)))
                .add(cos(radians(Expressions.constant(goRoadRequestDTO.getGoLatitude())))
                        .multiply(cos(radians(escal.escalLatitude)))
                        .multiply(cos(radians(Expressions.constant(goRoadRequestDTO.getGoLongitude())).subtract(
                                radians(escal.escalLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return Optional.ofNullable(
                queryFactory.select(Projections.constructor(SkResponseDTO.class, escal.escalLatitude,
                        escal.escalLongitude, Expressions.as(distanceExpression, distancePath)))
                        .from(escal)
                        .where(escal.info.region.eq(goRoadRequestDTO.getCurrentRegion()).and(escal.info.station.eq(goRoadRequestDTO.getCurrentStation())))
                        .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                        .fetchFirst()
        );
    }
}
