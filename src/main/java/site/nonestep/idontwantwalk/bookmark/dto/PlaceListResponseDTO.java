package site.nonestep.idontwantwalk.bookmark.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class PlaceListResponseDTO {
    private Long placeNo;
    private String placeNickName;
    private BigDecimal placeLatitude;
    private BigDecimal placeLongitude;
    private String placeAddress;
    private String placeColor;

    public PlaceListResponseDTO(Long placeNo, String placeNickName, BigDecimal placeLatitude, BigDecimal placeLongitude, String placeAddress, String placeColor) {
        this.placeNo = placeNo;
        this.placeNickName = placeNickName;
        this.placeLatitude = placeLatitude;
        this.placeLongitude = placeLongitude;
        this.placeAddress = placeAddress;
        this.placeColor = placeColor;
    }
}
