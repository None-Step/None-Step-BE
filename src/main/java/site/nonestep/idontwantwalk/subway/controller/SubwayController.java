package site.nonestep.idontwantwalk.subway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.nonestep.idontwantwalk.subway.dto.*;
import site.nonestep.idontwantwalk.subway.entity.Subway;
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

        log.info("호출된 API: {}", subwayLocationRequestDTO);

        List<SubwayLocationResponseDTO> subwayLocationResponseDTO = subwayService.location(subwayLocationRequestDTO.getLatitude(),
                subwayLocationRequestDTO.getLongitude(), subwayLocationRequestDTO.getRadius());

        log.info("호출된 API: {}", subwayLocationResponseDTO);
        return new ResponseEntity<>(subwayLocationResponseDTO, HttpStatus.OK);
    }

    // 역 1개의 정보 전체 조회

    // 엘리베이터 있는 역 조회
    @GetMapping("/elevator")
    public ResponseEntity<?> elevator(@ModelAttribute SubwayElevatorRequestDTO subwayElevatorRequestDTO){

        log.info("호출된 API: {}", subwayElevatorRequestDTO);

        List<SubwayElevatorResponseDTO> subwayElevatorResponseDTO = subwayService.elevator(subwayElevatorRequestDTO.getLatitude(),
                subwayElevatorRequestDTO.getLongitude(), subwayElevatorRequestDTO.getRadius());


        log.info("호출된 API: {}", subwayElevatorResponseDTO);
        return new ResponseEntity<>(subwayElevatorResponseDTO, HttpStatus.OK);
    }

    // 에스컬레이터 있는 역 조회
    @GetMapping("/escal")
    public ResponseEntity<?> escal(@ModelAttribute SubwayEscalRequestDTO subwayEscalRequestDTO){

        log.info("호출된 API: {}", subwayEscalRequestDTO);

        List<SubwayEscalResponseDTO> subwayEscalResponseDTO = subwayService.escal(subwayEscalRequestDTO.getLatitude(),
                subwayEscalRequestDTO.getLongitude(), subwayEscalRequestDTO.getRadius());

        log.info("호출된 API: {}", subwayEscalResponseDTO);

        return new ResponseEntity<>(subwayEscalResponseDTO, HttpStatus.OK);
    }

    // 휠체어 리프트 있는 역 조회
    @GetMapping("/lift")
    public ResponseEntity<?> lift(@ModelAttribute SubwayLiftRequestDTO subwayLiftRequestDTO){

        log.info("호출된 API: {}", subwayLiftRequestDTO);

        List<SubwayLiftResponseDTO> subwayLiftResponseDTO = subwayService.lift(subwayLiftRequestDTO.getLatitude(),
                subwayLiftRequestDTO.getLongitude(), subwayLiftRequestDTO.getRadius());

        log.info("호출된 API: {}", subwayLiftResponseDTO);

        return new ResponseEntity<>(subwayLiftResponseDTO, HttpStatus.OK);
    }

    // 역 내 화장실 조회
    @GetMapping("/toilet")
    public ResponseEntity<?> toilet(@ModelAttribute SubwayToiletRequestDTO subwayToiletRequestDTO){

        log.info("호출된 API: {}", subwayToiletRequestDTO);

        List<SubwayToiletResponseDTO> subwayToiletResponseDTO = subwayService.toilet(subwayToiletRequestDTO.getLatitude(),
                subwayToiletRequestDTO.getLongitude(), subwayToiletRequestDTO.getRadius());

        log.info("호출된 API: {}", subwayToiletResponseDTO);

        return new ResponseEntity<>(subwayToiletResponseDTO, HttpStatus.OK);
    }

    // 역 내 장애인 화장실 조회
    @GetMapping("/dif-toilet")
    public ResponseEntity<?> difToilet(@ModelAttribute SubwayDifToiletRequestDTO subwayDifToiletRequestDTO){

        log.info("호출된 API: {}", subwayDifToiletRequestDTO);

        List<SubwayDifToiletResponseDTO> subwayDifToiletResponseDTO = subwayService.difToilet(subwayDifToiletRequestDTO.getLatitude(),
                subwayDifToiletRequestDTO.getLongitude(), subwayDifToiletRequestDTO.getRadius());

        log.info("호출된 API: {}", subwayDifToiletResponseDTO);

        return new ResponseEntity<>(subwayDifToiletResponseDTO, HttpStatus.OK);
    }

    // 역 내 수유실 조회
    @GetMapping("/nursing-room")
    public ResponseEntity<?> nursingRoom(@ModelAttribute SubwayNursingRequestDTO subwayNursingRequestDTO){

        log.info("호출된 API: {}", subwayNursingRequestDTO);

        List<SubwayNursingResponseDTO> subwayNursingResponseDTO = subwayService.nursingRoom(subwayNursingRequestDTO.getLatitude(),
                subwayNursingRequestDTO.getLongitude(), subwayNursingRequestDTO.getRadius());

        log.info("호출된 API: {}", subwayNursingResponseDTO);

        return new ResponseEntity<>(subwayNursingResponseDTO, HttpStatus.OK);
    }

    // 역 내 ATM 조회
    @GetMapping("/atm")
    public ResponseEntity<?> atm(@ModelAttribute SubwayATMReqeustDTO subwayATMReqeustDTO){

        log.info("호출된 API: {}", subwayATMReqeustDTO);

        List<SubwayATMResponseDTO> subwayATMResponseDTO = subwayService.atm(subwayATMReqeustDTO.getLatitude(),
                subwayATMReqeustDTO.getLongitude(), subwayATMReqeustDTO.getRadius());

        log.info("호출된 API: {}", subwayATMResponseDTO);

        return new ResponseEntity<>(subwayATMResponseDTO, HttpStatus.OK);
    }

    // 역 내 제세동기 조회
    @GetMapping("/aed")
    public ResponseEntity<?> aed(@ModelAttribute SubwayAEDRequestDTO subwayAEDRequestDTO){

        log.info("호출된 API: {}", subwayAEDRequestDTO);

        List<SubwayAEDResponseDTO> subwayAEDResponseDTO = subwayService.aed(subwayAEDRequestDTO.getLatitude(),
                subwayAEDRequestDTO.getLongitude(), subwayAEDRequestDTO.getRadius());

        log.info("호출된 API: {}", subwayAEDResponseDTO);

        return new ResponseEntity<>(subwayAEDResponseDTO, HttpStatus.OK);
    }

    // 역 내 전동 휠체어 충전기 설비 조회
    @GetMapping("/charger")
    public ResponseEntity<?> charger(@ModelAttribute SubwayChargerRequestDTO subwayChargerRequestDTO){

        log.info("호출된 API: {}", subwayChargerRequestDTO);

        List<SubwayChargerResponseDTO> subwayChargerResponseDTO = subwayService.charger(subwayChargerRequestDTO.getLatitude(),
                subwayChargerRequestDTO.getLongitude(), subwayChargerRequestDTO.getRadius());

        log.info("호출된 API: {}", subwayChargerResponseDTO);

        return new ResponseEntity<>(subwayChargerResponseDTO, HttpStatus.OK);
    }

    // 역 내 고객센터 조회
    @GetMapping("/center")
    public ResponseEntity<?> center(@ModelAttribute SubwayCenterRequestDTO subwayCenterRequestDTO){

        log.info("호출된 API: {}", subwayCenterRequestDTO);

        List<SubwayCenterResponseDTO> subwayCenterResponseDTO = subwayService.center(subwayCenterRequestDTO.getLatitude(),
                subwayCenterRequestDTO.getLongitude(), subwayCenterRequestDTO.getRadius());

        log.info("호출된 API: {}", subwayCenterResponseDTO);

        return new ResponseEntity<>(subwayCenterResponseDTO, HttpStatus.OK);
    }

    // 역 1개 정보 전체 조회
    @GetMapping("/station-info")
    public ResponseEntity<?> station(@ModelAttribute SubwayStationInfoRequestDTO subwayStationInfoRequestDTO){

        log.info("호출된 API: {}", subwayStationInfoRequestDTO);

        SubwayStationInfoResponseDTO subwayStationInfoResponseDTO = subwayService.totalStationInfo(subwayStationInfoRequestDTO);

        log.info("호출된 API: {}", subwayStationInfoResponseDTO);

        return new ResponseEntity<>(subwayStationInfoResponseDTO, HttpStatus.OK);
    }

    // 지하철에 타고 있을 때, 어느 역인지 알아보기
    @GetMapping("/now-station")
    public ResponseEntity<?> nowStation(@ModelAttribute SubwayNowRequestDTO subwayNowRequestDTO){

        log.info("호출된 API: {}", subwayNowRequestDTO);

        SubwayNowResponseDTO subwayNowResponseDTO = subwayService.nowStation(subwayNowRequestDTO);

        log.info("호출된 API: {}", subwayNowResponseDTO);

        return new ResponseEntity<>(subwayNowResponseDTO, HttpStatus.OK);
    }

}
