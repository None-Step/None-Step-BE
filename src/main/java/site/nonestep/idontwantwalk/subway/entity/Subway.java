package site.nonestep.idontwantwalk.subway.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class Subway implements Serializable {

    private String line;
    private String region;
    private String station;
}
