package site.nonestep.idontwantwalk.congestion.repository;

import site.nonestep.idontwantwalk.congestion.dto.DownInfoResponseDTO;

import java.util.List;

public interface DownEtcRepositoryCustom {
    // 해당 역의 하행선 이격거리 및 추가 정보 조회
    List<DownInfoResponseDTO> selectDownInfo(String region, String line, String station);
}
