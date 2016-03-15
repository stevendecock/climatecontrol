package be.decock.steven.climatecontrol.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface ClimateDataHourRepository extends MongoRepository<ClimateDataHour, String> {

    ClimateDataHour findFirstByLocationOrderByStartTimeDesc(String location);

    List<ClimateDataHour> findByLocationOrderByStartTimeAsc(String location);

    List<ClimateDataHour> findByLocationAndStartTimeAfterOrderByStartTimeAsc(String location, Date from);

    List<ClimateDataHour> findByLocationAndStartTimeBeforeOrderByStartTimeAsc(String location, Date to);

    List<ClimateDataHour> findByLocationAndStartTimeBetweenOrderByStartTimeAsc(String location, Date from, Date to);

}
