package site.nonestep.idontwantwalk.congestion.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.congestion.dto.DownInfoResponseDTO;

import java.util.List;
import static site.nonestep.idontwantwalk.congestion.entity.QDownEtc.*;

public class DownEtcRepositoryImpl implements DownEtcRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // 해당 역의 하행선 이격거리 및 추가 정보 조회
    @Override
    public List<DownInfoResponseDTO> selectDownInfo(String region, String line, String station) {
        return queryFactory.select(Projections.constructor(DownInfoResponseDTO.class, downEtc.downEtcNo, downEtc.downEtcCondition,
                downEtc.downEtcElevator, downEtc.downEtcEscal, downEtc.downEtcStair, downEtc.downEtcBoardingBest))
                .from(downEtc)
                .where(downEtc.info.region.eq(region).and(downEtc.info.line.eq(line).and(downEtc.info.station.eq(station))))
                .fetch();
    }
}
