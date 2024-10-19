package site.nonestep.idontwantwalk.congestion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;
import site.nonestep.idontwantwalk.subway.entity.Info;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class DownCongestion {

    @Id
    @Column(name = "down_congestion_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long downCongestionNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Enumerated(EnumType.STRING)
    @Column(name = "down_congestion_type")
    private DayType downCongestionType;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_0530")
    private BigDecimal downCongestion0530;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_0600")
    private BigDecimal downCongestion0600;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_0630")
    private BigDecimal downCongestion0630;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_0700")
    private BigDecimal downCongestion0700;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_0730")
    private BigDecimal downCongestion0730;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_0800")
    private BigDecimal downCongestion0800;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_0830")
    private BigDecimal downCongestion0830;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_0900")
    private BigDecimal downCongestion0900;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_0930")
    private BigDecimal downCongestion0930;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1000")
    private BigDecimal downCongestion1000;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1030")
    private BigDecimal downCongestion1030;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1100")
    private BigDecimal downCongestion1100;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1130")
    private BigDecimal downCongestion1130;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1200")
    private BigDecimal downCongestion1200;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1230")
    private BigDecimal downCongestion1230;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1300")
    private BigDecimal downCongestion1300;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1330")
    private BigDecimal downCongestion1330;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1400")
    private BigDecimal downCongestion1400;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1430")
    private BigDecimal downCongestion1430;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1500")
    private BigDecimal downCongestion1500;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1530")
    private BigDecimal downCongestion1530;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1600")
    private BigDecimal downCongestion1600;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1630")
    private BigDecimal downCongestion1630;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1700")
    private BigDecimal downCongestion1700;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1730")
    private BigDecimal downCongestion1730;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1800")
    private BigDecimal downCongestion1800;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1830")
    private BigDecimal downCongestion1830;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1900")
    private BigDecimal downCongestion1900;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_1930")
    private BigDecimal downCongestion1930;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_2000")
    private BigDecimal downCongestion2000;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_2030")
    private BigDecimal downCongestion2030;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_2100")
    private BigDecimal downCongestion2100;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_2130")
    private BigDecimal downCongestion2130;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_2200")
    private BigDecimal downCongestion2200;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_2230")
    private BigDecimal downCongestion2230;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_2300")
    private BigDecimal downCongestion2300;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_2330")
    private BigDecimal downCongestion2330;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_0000")
    private BigDecimal downCongestion0000;

    @Digits(integer = 4, fraction = 5)
    @Column(name = "down_congestion_0030")
    private BigDecimal downCongestion0030;

    @Column(name = "down_next_station", length = 100)
    private String downNextStation;
}
