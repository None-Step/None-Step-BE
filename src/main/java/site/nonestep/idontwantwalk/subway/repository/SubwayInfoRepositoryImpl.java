package site.nonestep.idontwantwalk.subway.repository;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.road.dto.*;
import site.nonestep.idontwantwalk.subway.dto.SubwayLocationResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayNowResponseDTO;
import site.nonestep.idontwantwalk.subway.entity.Info;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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

    // 역 1개 정보 전체 조회
    @Override
    public Optional<Info> selectInfo(String region, String line, String station) {
        return Optional.ofNullable(
                queryFactory.select(info)
                        .from(info)
                        .where(info.region.eq(region).and(info.line.eq(line).and(info.station.eq(station))))
                        .fetchFirst()
        );
    }

    // 지하철 탑승 시 내가 어느 역인지 알아보기
    @Override
    public Optional<SubwayNowResponseDTO> selectStation(BigDecimal latitude, BigDecimal longitude) {

        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(latitude)))
                .multiply(sin(radians(info.infoLatitude)))
                .add(cos(radians(Expressions.constant(latitude)))
                        .multiply(cos(radians(info.infoLatitude)))
                        .multiply(cos(radians(Expressions.constant(longitude)).subtract(
                                radians(info.infoLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return Optional.ofNullable(
                queryFactory.select(Projections.constructor(SubwayNowResponseDTO.class, info.region,
                        info.line, info.station, Expressions.as(distanceExpression, distancePath)))
                        .from(info)
                        .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                        .fetchFirst()

        );
    }

    // [도보 길찾기] 지역, 호선을 입력하면 일치하면서 가장 가까운 지하철 기본 역의 위도와 경도를 return
    @Override
    public Optional<SkResponseDTO> selectNearByStation(GoStationRequestDTO goStationRequestDTO) {

        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(goStationRequestDTO.getCurrentLatitude())))
                .multiply(sin(radians(info.infoLatitude)))
                .add(cos(radians(Expressions.constant(goStationRequestDTO.getCurrentLatitude())))
                        .multiply(cos(radians(info.infoLatitude)))
                        .multiply(cos(radians(Expressions.constant(goStationRequestDTO.getCurrentLongitude())).subtract(
                                radians(info.infoLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return Optional.ofNullable(
                queryFactory.select(Projections.constructor(SkResponseDTO.class, info.infoLatitude,
                        info.infoLongitude, Expressions.as(distanceExpression, distancePath)))
                        .from(info)
                        .where(info.region.eq(goStationRequestDTO.getGoRegion()).and(info.station.eq(goStationRequestDTO.getGoStation())))
                        .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                        .fetchFirst()
        );
    }

    // [길 찾기: 역 > 목적지] 지역, 역 명을 넣으면 기본 역의 위도, 경도를 return
    @Override
    public Optional<SkResponseDTO> selectGoRoadStation(GoRoadRequestDTO goRoadRequestDTO) {

        NumberExpression<Double> distanceExpression = acos(sin(radians(Expressions.constant(goRoadRequestDTO.getGoLatitude())))
                .multiply(sin(radians(info.infoLatitude)))
                .add(cos(radians(Expressions.constant(goRoadRequestDTO.getGoLatitude())))
                        .multiply(cos(radians(info.infoLatitude)))
                        .multiply(cos(radians(Expressions.constant(goRoadRequestDTO.getGoLongitude())).subtract(
                                radians(info.infoLongitude))))
                )).multiply(6371000);
        Path<Double> distancePath = Expressions.numberPath(Double.class, "distance");

        return Optional.ofNullable(
                queryFactory.select(Projections.constructor(SkResponseDTO.class, info.infoLatitude,
                        info.infoLongitude, Expressions.as(distanceExpression, distancePath)))
                        .from(info)
                        .where(info.region.eq(goRoadRequestDTO.getCurrentRegion()).and(info.station.eq(goRoadRequestDTO.getCurrentStation())))
                        .orderBy(((ComparableExpressionBase<Double>) distancePath).asc())
                        .fetchFirst()
        );
    }

    // 지하철 경로 추출
    @Override
    public Optional<SubwayPathResponseDTOX> selectSidAndCid(String region, String line, String station) {
        return Optional.ofNullable(
                queryFactory.select(Projections.constructor(SubwayPathResponseDTOX.class, info.infoCID, info.infoSID))
                        .from(info)
                        .where(info.region.eq(region).and(info.line.eq(line).and(info.station.eq(station))))
                        .fetchFirst()
        );
    }

    // [목록] 지역, 호선, 역 명을 넣으면 해당 역의 위도, 경도 return
    @Override
    public Optional<StationListDTOX> selectLatitudeAndLongitude(GoListRequestDTO goListRequestDTO) {
        return Optional.ofNullable(
                queryFactory.select(Projections.constructor(StationListDTOX.class, info.infoLatitude, info.infoLongitude))
                        .from(info)
                        .where(info.region.eq(goListRequestDTO.getGoRegion()).and(info.line.eq(goListRequestDTO.getGoLine())
                                .and(info.station.eq(goListRequestDTO.getGoStation()))))
                        .fetchFirst()
        );
    }
}
