package site.nonestep.idontwantwalk.road.subwaypath;

import lombok.Data;

@Data
public class Stations {
    Long startID;
    String startName;
    Long endSID;
    String endName;
    Long travelTime;
}
