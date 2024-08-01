package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.SubwayLocationResponseDTO;
import site.nonestep.idontwantwalk.subway.entity.Info;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SubwayInfoRepositoryCustom {

    // 위치 기반 역 조회 및 검색
    List<SubwayLocationResponseDTO> selectInfoLatitudeAndInfoLongitudeAndRadius(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 1개 정보 전체 조회
    Optional<Info> selectInfo(String region, String line, String station);
}
