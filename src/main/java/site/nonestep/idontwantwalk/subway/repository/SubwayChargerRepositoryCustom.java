package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.InfoCharger;
import site.nonestep.idontwantwalk.subway.dto.SubwayChargerResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayChargerRepositoryCustom {

    // 역 내 전동 휠체어 충전 설비 조회
    List<SubwayChargerResponseDTO> selectCharger(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 1개 정보 전체 조회
    List<InfoCharger> selectInfoCharger(String region, String line, String station);
}
