package site.nonestep.idontwantwalk.congestion.service;

import site.nonestep.idontwantwalk.congestion.dto.DownTimeRequestDTO;
import site.nonestep.idontwantwalk.congestion.dto.DownTimeResponseDTO;
import site.nonestep.idontwantwalk.congestion.dto.UpTimeRequestDTO;
import site.nonestep.idontwantwalk.congestion.dto.UpTimeResponseDTO;

public interface CongestionService {

    // 해당 역의 상행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤
    UpTimeResponseDTO upTime(UpTimeRequestDTO upTimeRequestDTO);

    // 해당 역의 하행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤
    DownTimeResponseDTO downTime(DownTimeRequestDTO downTimeRequestDTO);
}
