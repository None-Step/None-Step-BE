package site.nonestep.idontwantwalk.congestion.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.nonestep.idontwantwalk.congestion.dto.*;
import site.nonestep.idontwantwalk.congestion.service.CongestionService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/congestion")
public class CongestionController {

    @Autowired
    private CongestionService congestionService;

    // // 해당 역의 상행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤
    @GetMapping("/up-time")
    public ResponseEntity<?> upTime(@ModelAttribute UpTimeRequestDTO upTimeRequestDTO) {
        UpTimeResponseDTO upTime = congestionService.upTime(upTimeRequestDTO);
        if (upTime == null) {
            return new ResponseEntity<>("정보가 없는 역이거나 잘못 입력하셨습니다.", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(upTime, HttpStatus.OK);
        }
    }

    // 해당 역의 하행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤
    @GetMapping("/down-time")
    public ResponseEntity<?> downTime(@ModelAttribute DownTimeRequestDTO downTimeRequestDTO) {
        DownTimeResponseDTO downTime = congestionService.downTime(downTimeRequestDTO);

        if (downTime == null) {
            return new ResponseEntity<>("정보가 없는 역이거나 잘못 입력하셨습니다.", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(downTime, HttpStatus.OK);
        }
    }

    // 역 상행선 이격 거리 및 추가 정보 API
    @GetMapping("/up-info")
    public ResponseEntity<?> upInfo(@ModelAttribute UpInfoRequestDTO upInfoRequestDTO) {

        List<UpInfoResponseDTO> upInfo = congestionService.upInfo(upInfoRequestDTO);

        if (upInfo.isEmpty()) {
            return new ResponseEntity<>("해당 데이터가 존재하지 않거나 오류입니다.", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(upInfo, HttpStatus.OK);
        }
    }

    // 역 하행선 이격 거리 및 추가 정보 API
    @GetMapping("/down-info")
    public ResponseEntity<?> downInfo(@ModelAttribute DownInfoRequestDTO downInfoRequestDTO) {

        List<DownInfoResponseDTO> downInfo = congestionService.downInfo(downInfoRequestDTO);

        if (downInfo.isEmpty()) {
            return new ResponseEntity<>("해당 데이터가 존재하지 않거나 오류입니다.", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(downInfo, HttpStatus.OK);
        }
    }

    // 역 혼잡도 API - 지도 마커용
    @GetMapping("/subway-marker")
    public ResponseEntity<?> subwayMarker(@ModelAttribute SubwayMarkerRequestDTO subwayMarkerRequestDTO) {
        List<SubwayMarkerResponseDTO> subwayMark = congestionService.subwayMark(subwayMarkerRequestDTO);

        return new ResponseEntity<>(subwayMark,HttpStatus.OK);
    }

    // 상행선 칸 별 혼잡도(10분 단위)
    @GetMapping("/up-car")
    public ResponseEntity<?> upCar(@ModelAttribute CarRequestDTO carRequestDTO) {
        CarResponseDTO carCongestionInfo = congestionService.carCongestionInfo(carRequestDTO,0);
        return new ResponseEntity<>(carCongestionInfo,HttpStatus.OK);
    }

    // 하행선 칸 별 혼잡도(10분 단위)
    @GetMapping("/down-car")
    public ResponseEntity<?> downCar(@ModelAttribute CarRequestDTO carRequestDTO) {
        CarResponseDTO carCongestionInfo = congestionService.carCongestionInfo(carRequestDTO,1);
        return new ResponseEntity<>(carCongestionInfo,HttpStatus.OK);
    }
}

