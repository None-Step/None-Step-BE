package site.nonestep.idontwantwalk.bookmark.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class PathListResponseDTO {
    private Long pathNo;
    private String pathStartNickName;
    private String pathEndNickName;
    private BigDecimal pathStartLatitude;
    private BigDecimal pathStartLongitude;
    private BigDecimal pathEndLatitude;
    private BigDecimal pathEndLongitude;
    private String pathColor;

    public PathListResponseDTO(Long pathNo, String pathStartNickName, String pathEndNickName, BigDecimal pathStartLatitude, BigDecimal pathStartLongitude, BigDecimal pathEndLatitude, BigDecimal pathEndLongitude, String pathColor) {
        this.pathNo = pathNo;
        this.pathStartNickName = pathStartNickName;
        this.pathEndNickName = pathEndNickName;
        this.pathStartLatitude = pathStartLatitude;
        this.pathStartLongitude = pathStartLongitude;
        this.pathEndLatitude = pathEndLatitude;
        this.pathEndLongitude = pathEndLongitude;
        this.pathColor = pathColor;
    }
}
