package site.nonestep.idontwantwalk.congestion.repository;

import site.nonestep.idontwantwalk.congestion.dto.UpTimeResponseDTO;
import site.nonestep.idontwantwalk.congestion.entity.DayType;
import site.nonestep.idontwantwalk.congestion.entity.UpCongestion;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UpCongestionRepositoryCustom {

    // 해당 역의 상행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤 조회
    UpCongestion selectCurrentTimeTo1Hours(String region, String line, String station,  DayType type);
}
