package site.nonestep.idontwantwalk.road.subwaypath;

import lombok.Data;

@Data
public class Result {
    String globalStartName;
    String globalEndName;
    Long globalTravelTime;
    Double globalDistance;
    Long globalStationCount;
    Long fare;
    Long cashFare;
    DrivenInfoSet driveInfoSet;
    ExChangeInfoSet exChangeInfoSet;
    StationSet stationSet;
}
