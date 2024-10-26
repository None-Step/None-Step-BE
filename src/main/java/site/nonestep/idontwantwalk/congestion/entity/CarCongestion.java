package site.nonestep.idontwantwalk.congestion.entity;

import jakarta.persistence.*;
import lombok.*;
import site.nonestep.idontwantwalk.subway.entity.Info;

import java.time.LocalTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class CarCongestion {
    @Id
    @Column(name = "car_congestion_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carCongestionNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_day")
    private DayType carDay;

    @Column(name = "car_time")
    private LocalTime carTime;

    @Column(name = "car_index")
    private Integer carIndex;

    @Column(name = "car_congestion")
    private String carCongestion;

    @Column(name = "car_direction")
    private Integer carDirection;

}
