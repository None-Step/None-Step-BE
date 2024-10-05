package site.nonestep.idontwantwalk.bookmark.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class PathRegisterRequestDTO {
    private BigDecimal startLatitude;
    private BigDecimal startLongitude;
    private BigDecimal endLatitude;
    private BigDecimal endLongitude;
    private String pathStartNickName;
    private String pathEndNickName;
    private String pathColor;
}
