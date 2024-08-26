package site.nonestep.idontwantwalk.road.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class SubwayPathResponseDTOX {
    private int cid;
    private int sid;

    public SubwayPathResponseDTOX(int cid, int sid) {
        this.cid = cid;
        this.sid = sid;
    }
}
