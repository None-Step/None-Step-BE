package site.nonestep.idontwantwalk.road.gsonClass;

import com.nimbusds.jose.shaded.gson.JsonElement;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
@Data
public class Geometry {
    String type;
    List<Object> coordinates;
}
