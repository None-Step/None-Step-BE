package site.nonestep.idontwantwalk.road.subwaypath;

import lombok.Data;

@Data
public class DriveInfo {
    String laneID;
    String laneName;
    String startName;
    Long stationCount;
    Long wayCode;
    String wayName;
}
