package site.nonestep.idontwantwalk.bookmark.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class PlaceRegisterRequestDTO {

    private BigDecimal latitude;
    private BigDecimal longitude;
    private String placeNickName;
    private String placeAddress;
    private String placeColor;
}
