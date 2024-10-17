package site.nonestep.idontwantwalk.congestion.repository;

import com.querydsl.core.Tuple;
import site.nonestep.idontwantwalk.congestion.dto.SubwayMarkerResponseDTO;
import site.nonestep.idontwantwalk.congestion.entity.DayType;
import site.nonestep.idontwantwalk.congestion.entity.DownCongestion;

import java.math.BigDecimal;
import java.util.List;

public interface DownCongestionRepositoryCustom {

    // 해당 역의 하행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤
    DownCongestion selectCurrentTimeTo1Hours(String region, String line, String station, DayType type);

    // 역 혼잡도 API - 마커용
    List<Tuple>  selectSubwayInfoAndCongestion(BigDecimal latitude, BigDecimal longitude, Long radius, DayType type);
}
