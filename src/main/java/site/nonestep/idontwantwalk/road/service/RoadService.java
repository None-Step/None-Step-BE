package site.nonestep.idontwantwalk.road.service;

import site.nonestep.idontwantwalk.road.dto.SubwayPathRequestDTO;
import site.nonestep.idontwantwalk.road.dto.SubwayPathResponseDTOX;

public interface RoadService {

    // 지역, 호선, 역 명으로 cid, sid 빼오기
    SubwayPathResponseDTOX selectCidAndSid(String region, String line, String station);
}
