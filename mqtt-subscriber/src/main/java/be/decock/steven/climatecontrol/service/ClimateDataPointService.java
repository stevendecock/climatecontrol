package be.decock.steven.climatecontrol.service;

import be.decock.steven.climatecontrol.data.IClimateDataPoint;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface ClimateDataPointService <CDP extends IClimateDataPoint> {

    List<CDP> findByLocation(String location);

    List<CDP> findByLocationAndStartTimeAfter(String location, Date from);

    List<CDP> findByLocationAndStartTimeBefore(String location, Date to);

    List<CDP> findByLocationAndStartTimeBetween(String location, Date from, Date to);

}
