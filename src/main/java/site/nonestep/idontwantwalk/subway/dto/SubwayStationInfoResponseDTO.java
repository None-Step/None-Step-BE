package site.nonestep.idontwantwalk.subway.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
public class SubwayStationInfoResponseDTO {
    private String infoRegion;
    private String infoLine;
    private String infoStation;
    private String infoAddress;
    private BigDecimal infoLatitude;
    private BigDecimal infoLongitude;
    private String infoTransfer;
    private String infoWeekDayStart;
    private String infoWeekDayEnd;
    private String infoSatStart;
    private String infoSatEnd;
    private String infoHolidayStart;
    private String infoHolidayEnd;
    private List<InfoEscal> escal;
    private List<InfoElevator> elevator;
    private List<InfoToilet> toilet;
    private List<InfoDifToilet> difToilet;
    private List<InfoLift> lift;
    private List<InfoCharger> charger;
    private List<InfoNursing> nursingRoom;
    private List<InfoAtm> atm;
    private List<InfoAed> aed;
    private List<InfoCenter> center;
}
