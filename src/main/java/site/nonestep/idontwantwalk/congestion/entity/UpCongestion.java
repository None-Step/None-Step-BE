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
public class UpCongestion {

    @Id
    @Column(name = "up_congestion_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long upCongestionNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Enumerated(EnumType.STRING)
    @Column(name = "up_congestion_type")
    private DayType upCongestionType;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_0530")
    private BigDecimal upCongestion0530;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_0600")
    private BigDecimal upCongestion0600;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_0630")
    private BigDecimal upCongestion0630;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_0700")
    private BigDecimal upCongestion0700;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_0730")
    private BigDecimal upCongestion0730;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_0800")
    private BigDecimal upCongestion0800;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_0830")
    private BigDecimal upCongestion0830;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_0900")
    private BigDecimal upCongestion0900;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_0930")
    private BigDecimal upCongestion0930;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1000")
    private BigDecimal upCongestion1000;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1030")
    private BigDecimal upCongestion1030;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1100")
    private BigDecimal upCongestion1100;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1130")
    private BigDecimal upCongestion1130;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1200")
    private BigDecimal upCongestion1200;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1230")
    private BigDecimal upCongestion1230;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1300")
    private BigDecimal upCongestion1300;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1330")
    private BigDecimal upCongestion1330;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1400")
    private BigDecimal upCongestion1400;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1430")
    private BigDecimal upCongestion1430;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1500")
    private BigDecimal upCongestion1500;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1530")
    private BigDecimal upCongestion1530;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1600")
    private BigDecimal upCongestion1600;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1630")
    private BigDecimal upCongestion1630;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1700")
    private BigDecimal upCongestion1700;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1730")
    private BigDecimal upCongestion1730;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1800")
    private BigDecimal upCongestion1800;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1830")
    private BigDecimal upCongestion1830;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1900")
    private BigDecimal upCongestion1900;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_1930")
    private BigDecimal upCongestion1930;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_2000")
    private BigDecimal upCongestion2000;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_2030")
    private BigDecimal upCongestion2030;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_2100")
    private BigDecimal upCongestion2100;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_2130")
    private BigDecimal upCongestion2130;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_2200")
    private BigDecimal upCongestion2200;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_2230")
    private BigDecimal upCongestion2230;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_2300")
    private BigDecimal upCongestion2300;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_2330")
    private BigDecimal upCongestion2330;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_0000")
    private BigDecimal upCongestion0000;

    @Digits(integer = 4, fraction = 3)
    @Column(name = "up_congestion_0030")
    private BigDecimal upCongestion0030;
}
