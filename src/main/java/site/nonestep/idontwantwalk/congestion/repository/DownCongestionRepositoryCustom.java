package site.nonestep.idontwantwalk.congestion.repository;

import site.nonestep.idontwantwalk.congestion.entity.DayType;
import site.nonestep.idontwantwalk.congestion.entity.DownCongestion;

public interface DownCongestionRepositoryCustom {

    // 해당 역의 하행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤
    DownCongestion selectCurrentTimeTo1Hours(String region, String line, String station, DayType type);
}
