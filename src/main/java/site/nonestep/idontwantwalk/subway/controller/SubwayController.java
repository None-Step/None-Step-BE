package site.nonestep.idontwantwalk.subway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.nonestep.idontwantwalk.subway.dto.*;
import site.nonestep.idontwantwalk.subway.service.SubwayService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/subway")
public class SubwayController {

    @Autowired
    private SubwayService subwayService;

    // 위치 기반 역 조회 및 검색
    @GetMapping("/location")
    public ResponseEntity<?> location(@ModelAttribute SubwayLocationRequestDTO subwayLocationRequestDTO){
        List<SubwayLocationResponseDTO> subwayLocationResponseDTO = subwayService.location(subwayLocationRequestDTO.getLatitude(),
                subwayLocationRequestDTO.getLongitude(), subwayLocationRequestDTO.getRadius());

        return new ResponseEntity<>(subwayLocationResponseDTO, HttpStatus.OK);
    }

    // 역 1개의 정보 전체 조회

    // 엘리베이터 있는 역 조회
    @GetMapping("/elevator")
    public ResponseEntity<?> elevator(@ModelAttribute SubwayElevatorRequestDTO subwayElevatorRequestDTO){
        List<SubwayElevatorResponseDTO> subwayElevatorResponseDTO = subwayService.elevator(subwayElevatorRequestDTO.getLatitude(),
                subwayElevatorRequestDTO.getLongitude(), subwayElevatorRequestDTO.getRadius());

        return new ResponseEntity<>(subwayElevatorResponseDTO, HttpStatus.OK);
    }

    // 에스컬레이터 있는 역 조회
    @GetMapping("/escal")
    public ResponseEntity<?> escal(@ModelAttribute SubwayEscalRequestDTO subwayEscalRequestDTO){
        List<SubwayEscalResponseDTO> subwayEscalResponseDTO = subwayService.escal(subwayEscalRequestDTO.getLatitude(),
                subwayEscalRequestDTO.getLongitude(), subwayEscalRequestDTO.getRadius());

        return new ResponseEntity<>(subwayEscalResponseDTO, HttpStatus.OK);
    }

    // 휠체어 리프트 있는 역 조회
    @GetMapping("/lift")
    public ResponseEntity<?> lift(@ModelAttribute SubwayLiftRequestDTO subwayLiftRequestDTO){
        List<SubwayLiftResponseDTO> subwayLiftResponseDTO = subwayService.lift(subwayLiftRequestDTO.getLatitude(),
                subwayLiftRequestDTO.getLongitude(), subwayLiftRequestDTO.getRadius());

        return new ResponseEntity<>(subwayLiftResponseDTO, HttpStatus.OK);
    }

    // 역 내 화장실 조회
    @GetMapping("/toilet")
    public ResponseEntity<?> toilet(@ModelAttribute SubwayToiletRequestDTO subwayToiletRequestDTO){
        List<SubwayToiletResponseDTO> subwayToiletResponseDTO = subwayService.toilet(subwayToiletRequestDTO.getLatitude(),
                subwayToiletRequestDTO.getLongitude(), subwayToiletRequestDTO.getRadius());

        return new ResponseEntity<>(subwayToiletResponseDTO, HttpStatus.OK);
    }


}
