package site.nonestep.idontwantwalk.subway.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@IdClass(Subway.class)
public class Info {

    @Id
    @Column(name = "region")
    private String region;

    @Id
    @Column(name = "line")
    private String line;

    @Id
    @Column(name = "station")
    private String station;

    @Column(name = "info_address", length = 500, nullable = false)
    private String infoAddress;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "info_latitude", nullable = false)
    private BigDecimal infoLatitude;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "info_longitude", nullable = false)
    private BigDecimal infoLongitude;

    @Column(name = "info_transfer", length = 200)
    private String infoTransfer;

    @Column(name = "info_weekday_start", length = 50)
    private String infoWeekdayStart;

    @Column(name = "info_weekday_end", length = 50)
    private String infoWeekdayEnd;

    @Column(name = "info_sat_start", length = 50)
    private String infoSatStart;

    @Column(name = "info_sat_end", length = 50)
    private String infoSatEnd;

    @Column(name = "info_holiday_start", length = 50)
    private String infoHolidayStart;

    @Column(name = "info_holiday_end", length = 50)
    private String infoHolidayEnd;

    @Column(name = "info_cid", nullable = false)
    private int infoCID;

    @Column(name = "info_sid", nullable = false)
    private int infoSID;

    @Column(name = "info_climate_get_on", length = 10)
    private String infoClimateGetOn;

    @Column(name = "info_climate_get_off", length = 10)
    private String infoClimateGetOff;

}
