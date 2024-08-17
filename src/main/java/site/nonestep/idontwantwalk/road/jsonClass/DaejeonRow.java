package site.nonestep.idontwantwalk.road.jsonClass;

import lombok.Data;

@Data
public class DaejeonRow {
    String id;
    String name;
    String name_en;
    String name_cn;
    String x_pos;
    String y_pos;
    String address;
    Long parking_count;
}
