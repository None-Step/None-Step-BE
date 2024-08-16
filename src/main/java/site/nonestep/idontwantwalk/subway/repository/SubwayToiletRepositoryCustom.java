package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.InfoToilet;
import site.nonestep.idontwantwalk.subway.dto.SubwayToiletResponseDTO;
import site.nonestep.idontwantwalk.subway.entity.Toilet;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayToiletRepositoryCustom {

    // 역 내 화장실 조회
    List<SubwayToiletResponseDTO> selectToilet(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 1개 정보 전체 조회
    List<InfoToilet> selectInfoToilet(String region, String line, String station);
}
