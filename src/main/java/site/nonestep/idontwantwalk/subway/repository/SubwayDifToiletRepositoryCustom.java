package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.SubwayDifToiletResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayDifToiletRepositoryCustom {
    // 역 내 장애인 화장실 조회
    List<SubwayDifToiletResponseDTO> selectDifToilet(BigDecimal latitude, BigDecimal longitude, Long radius);
}