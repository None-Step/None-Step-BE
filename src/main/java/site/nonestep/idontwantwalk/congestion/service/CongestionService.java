package site.nonestep.idontwantwalk.congestion.service;

import site.nonestep.idontwantwalk.congestion.dto.*;

import java.util.List;

public interface CongestionService {

    // 해당 역의 상행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤
    UpTimeResponseDTO upTime(UpTimeRequestDTO upTimeRequestDTO);

    // 해당 역의 하행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤
    DownTimeResponseDTO downTime(DownTimeRequestDTO downTimeRequestDTO);

    // 해당 역의 상행선 이격거리 및 추가 정보
    List<UpInfoResponseDTO> upInfo(UpInfoRequestDTO upInfoRequestDTO);

    // 해당 역의 상행선 이격거리 및 추가 정보
    List<DownInfoResponseDTO> downInfo(DownInfoRequestDTO downInfoRequestDTO);

    // 역 혼잡도 - 마커용
    List<SubwayMarkerResponseDTO> subwayMark(SubwayMarkerRequestDTO subwayMarkerRequestDTO);
}
