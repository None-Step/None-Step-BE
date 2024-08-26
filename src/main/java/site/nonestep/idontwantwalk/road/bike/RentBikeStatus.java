package site.nonestep.idontwantwalk.road.bike;

import lombok.Data;

import java.util.List;

@Data
public class RentBikeStatus {
    Long list_total_count;
    Result RESULT;
    List<Row> row;

}
