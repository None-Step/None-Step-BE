package site.nonestep.idontwantwalk.road.gsonClass;

import lombok.Data;

@Data
public class Features {
    String type;
    Geometry geometry;
    Properties properties;
}
