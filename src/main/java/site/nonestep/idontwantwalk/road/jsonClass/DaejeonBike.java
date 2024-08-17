package site.nonestep.idontwantwalk.road.jsonClass;

import lombok.Data;

import java.util.List;

@Data
public class DaejeonBike {
    Long count;
    List<DaejeonRow> results;
}
