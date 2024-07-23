package site.nonestep.idontwantwalk.subway.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Elevator {

    @Id
    @Column(name = "elevator_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long elevatorNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Column(name = "elevator_comment", length = 500)
    private String elevatorComment;

    @Digits(integer = 5, fraction = 15)
    @Column(name = "elevator_latitude")
    private BigDecimal elevatorLatitude;

    @Digits(integer = 5, fraction = 15)
    @Column(name = "elevator_longitude")
    private BigDecimal elevatorLongitude;
}
