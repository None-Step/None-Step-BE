package site.nonestep.idontwantwalk.congestion.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.congestion.dto.UpInfoResponseDTO;
import site.nonestep.idontwantwalk.congestion.entity.UpCongestion;
import site.nonestep.idontwantwalk.congestion.entity.UpEtc;

import java.util.List;
import java.util.Optional;

import static site.nonestep.idontwantwalk.congestion.entity.QUpEtc.*;

public class UpEtcRepositoryImpl implements UpEtcRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    // 해당 역의 상행선 이격거리 및 추가 정보 전체 조회
    @Override
    public List<UpInfoResponseDTO> selectUpInfo(String region, String line, String station) {
        return queryFactory.select(Projections.constructor(UpInfoResponseDTO.class, upEtc.upEtcNo, upEtc.upEtcCondition, upEtc.upEtcElevator,
                        upEtc.upEtcEscal, upEtc.upEtcStair, upEtc.upEtcBoardingBest))
                .from(upEtc)
                .where(upEtc.info.region.eq(region).and(upEtc.info.line.eq(line).and(upEtc.info.station.eq(station))))
                .fetch();
    }
}
