package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.SubwayToiletResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayToiletRepositoryCustom {

    // 역 내 화장실 조회
    List<SubwayToiletResponseDTO> selectToilet(BigDecimal latitude, BigDecimal longitude, Long radius);
}
