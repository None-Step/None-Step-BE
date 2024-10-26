package site.nonestep.idontwantwalk.congestion.repository;

import site.nonestep.idontwantwalk.congestion.entity.CarCongestion;
import site.nonestep.idontwantwalk.congestion.entity.DayType;

import java.time.LocalTime;
import java.util.List;

public interface CarCongestionRepositoryCustom {

    List<CarCongestion> selectCarCongestion(String region, String line, String station, DayType dayType, LocalTime localTime, int dir);
}
