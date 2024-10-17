package site.nonestep.idontwantwalk.congestion.repository;

import site.nonestep.idontwantwalk.congestion.dto.UpInfoResponseDTO;
import site.nonestep.idontwantwalk.congestion.entity.UpCongestion;
import site.nonestep.idontwantwalk.congestion.entity.UpEtc;

import java.util.List;
import java.util.Optional;

public interface UpEtcRepositoryCustom {

    // 해당 역의 상행선 이격거리 및 추가 정보 전체 조회
    List<UpInfoResponseDTO> selectUpInfo(String region, String line, String station);
}
