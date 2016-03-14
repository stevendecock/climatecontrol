package be.decock.steven.climatecontrol.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface ClimateDataHourRepository extends MongoRepository<ClimateDataHour, String> {


    ClimateDataHour findFirstByLocationOrderByStartTimeDesc(String location);

    List<ClimateDataHour> findByLocation(String location);

    List<ClimateDataHour> findByLocationAndStartTimeAfter(String location, Date from);

    List<ClimateDataHour> findByLocationAndStartTimeBefore(String location, Date to);

    List<ClimateDataHour> findByLocationAndStartTimeBetween(String location, Date from, Date to);

}
