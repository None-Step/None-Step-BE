package site.nonestep.idontwantwalk.road.dto;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class SkResponseDTO {
    private BigDecimal latitude;
    private BigDecimal longitude;
    private double distance;

    public SkResponseDTO(BigDecimal latitude, BigDecimal longitude, double distance) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    // 생성자가 생기면 이것만 쓸 수 있음
    // 기본도 같이 만들어준다.
    public SkResponseDTO(){

    }
}
