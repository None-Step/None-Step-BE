package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.InfoLift;
import site.nonestep.idontwantwalk.subway.dto.SubwayLiftResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayLiftRepositoryCustom {
    // 휠체어 리프트 있는 역 조회
    List<SubwayLiftResponseDTO> selectLift(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 1개 정보 전체 조회 시 필요
    List<InfoLift> selectInfoLift(String region, String line, String station);
}
