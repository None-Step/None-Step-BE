package site.nonestep.idontwantwalk.congestion.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.nonestep.idontwantwalk.congestion.dto.DownTimeRequestDTO;
import site.nonestep.idontwantwalk.congestion.dto.DownTimeResponseDTO;
import site.nonestep.idontwantwalk.congestion.dto.UpTimeRequestDTO;
import site.nonestep.idontwantwalk.congestion.dto.UpTimeResponseDTO;
import site.nonestep.idontwantwalk.congestion.service.CongestionService;

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
}
