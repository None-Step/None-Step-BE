package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.aspectj.weaver.ast.Expr;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.road.dto.GoRoadRequestDTO;
import site.nonestep.idontwantwalk.road.dto.GoStationRequestDTO;
import site.nonestep.idontwantwalk.road.dto.SkResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.InfoElevator;
import site.nonestep.idontwantwalk.subway.dto.SubwayElevatorResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayLocationResponseDTO;
import site.nonestep.idontwantwalk.subway.entity.Elevator;
import static site.nonestep.idontwantwalk.subway.entity.QElevator.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.dsl.MathExpressions.*;
import static com.querydsl.core.types.dsl.MathExpressions.radians;

public class SubwayElevatorRepositoryImpl implements SubwayElevatorRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // 엘리베이터 있는 역 조회
    @Override
    public List<SubwayElevatorResponseDTO> selectElevator(BigDecimal latitude, BigDecimal longitude, Long radius) {
        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(elevator.elevatorLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(elevator.elevatorLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(elevator.elevatorLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return queryFactory.select(Projections.constructor(SubwayElevatorResponseDTO.class, elevator.info.region, elevator.info.line,
                        elevator.info.station, elevator.elevatorComment, elevator.elevatorAddress, elevator.elevatorLatitude,
                        elevator.elevatorLongitude, Expressions.as(distanceExpression, distancePath)))
                .from(elevator)
                .where(distanceExpression.loe(radius))
                .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                .fetch();
    }

    // 역 1개 정보 전체 조회 시 필요
    @Override
    public List<InfoElevator> selectInfoElevator(String region, String line, String station) {
        return queryFactory.select(Projections.constructor(InfoElevator.class, elevator.elevatorComment))
                .from(elevator)
                .where(elevator.info.region.eq(region).and(elevator.info.line.eq(line).and(elevator.info.station.eq(station))))
                .fetch();
    }

    // 지역, 호선, 역 명을 입력하면 역의 가장 가까운 엘리베이터의 위도, 경도를 return
    @Override
    public Optional<SkResponseDTO> selectNearByElevator(GoStationRequestDTO goStationRequestDTO) {
        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(goStationRequestDTO.getCurrentLatitude())))
                .multiply(sin(radians(elevator.elevatorLatitude)))
                .add(cos(radians(Expressions.constant(goStationRequestDTO.getCurrentLatitude())))
                        .multiply(cos(radians(elevator.elevatorLatitude)))
                        .multiply(cos(radians(Expressions.constant(goStationRequestDTO.getCurrentLongitude())).subtract(
                                radians(elevator.elevatorLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return Optional.ofNullable(
                queryFactory.select(Projections.constructor(SkResponseDTO.class, elevator.elevatorLatitude,
                        elevator.elevatorLongitude, Expressions.as(distanceExpression, distancePath)))
                        .from(elevator)
                        .where(elevator.info.region.eq(goStationRequestDTO.getGoRegion()).and(elevator.info.station.eq(goStationRequestDTO.getGoStation())))
                        .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                        .fetchFirst()
        );
    }

    // [길 찾기: 역 > 목적지] 지역, 역 명을 넣으면 기본 역의 가장 가까운 엘리베이터의 위도, 경도를 return 하는 것
    @Override
    public Optional<SkResponseDTO> selectGoRoadElevator(GoRoadRequestDTO goRoadRequestDTO) {

        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(goRoadRequestDTO.getGoLatitude())))
                .multiply(sin(radians(elevator.elevatorLatitude)))
                .add(cos(radians(Expressions.constant(goRoadRequestDTO.getGoLatitude())))
                        .multiply(cos(radians(elevator.elevatorLatitude)))
                        .multiply(cos(radians(Expressions.constant(goRoadRequestDTO.getGoLongitude())).subtract(
                                radians(elevator.elevatorLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return Optional.ofNullable(
                queryFactory.select(Projections.constructor(SkResponseDTO.class, elevator.elevatorLatitude,
                        elevator.elevatorLongitude, Expressions.as(distanceExpression, distancePath)))
                        .from(elevator)
                        .where(elevator.info.region.eq(goRoadRequestDTO.getCurrentRegion()).and(elevator.info.station.eq(goRoadRequestDTO.getCurrentStation())))
                        .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                        .fetchFirst()
        );
    }
}
