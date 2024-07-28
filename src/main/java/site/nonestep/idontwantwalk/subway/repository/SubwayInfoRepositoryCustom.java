package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.SubwayLocationResponseDTO;
import site.nonestep.idontwantwalk.subway.entity.Info;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayInfoRepositoryCustom {

    // 위치 기반 역 조회 및 검색
    List<SubwayLocationResponseDTO> selectInfoLatitudeAndInfoLongitudeAndRadius(BigDecimal latitude, BigDecimal longitude, Long radius);
}
