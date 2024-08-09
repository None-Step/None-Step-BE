package site.nonestep.idontwantwalk.road.gsonClass;

import lombok.Data;

@Data
public class Properties {
    // 첫 행만 있음 - point빠져서 null로 들어옴
//    Long totalDistance;
    // 첫 행만 있음 - - point빠져서 null로 들어옴
//    Long totalTime;
    // 이 아래로는 번갈아서 나옴
//    Long index;
//    Long pointIndex;
    String name;
//    String description;
//    String direction;
//    String nearPoiName;
//    String nearPoiX;
//    String nearPoiY;
//    String intersectionName;
//    String facilityType;
//    String facilityName;
//    Long turnType;
//    String pointType;
    Long lineIndex;
    Long distance;
    Long time;
//    Long roadType;
//    Long categoryRoadType;





}
