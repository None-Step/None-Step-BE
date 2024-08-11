package site.nonestep.idontwantwalk.road.gsonClass;

import lombok.Data;

import java.util.List;

@Data
public class GoRoad {

    String type;
    List<Features> features;
}
