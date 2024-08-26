package site.nonestep.idontwantwalk.road.subwaypath;

import lombok.Data;

@Data
public class ExchangeInfo {
    String laneName;
    String startName;
    String exName;
    Long exSID;
    Long fastTrain;
    Long fastDoor;
    Long exWalkTime;
}
