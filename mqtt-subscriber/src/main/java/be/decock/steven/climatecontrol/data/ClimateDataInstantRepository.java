package be.decock.steven.climatecontrol.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface ClimateDataInstantRepository extends MongoRepository<ClimateDataInstant, String> {

    List<ClimateDataInstant> findAllByOrderByTimeAsc();

    List<ClimateDataInstant> findByLocation(String location);

    List<ClimateDataInstant> findByLocationAndTimeAfter(String location, Date from);

    List<ClimateDataInstant> findByLocationAndTimeBefore(String location, Date to);

    List<ClimateDataInstant> findByLocationAndTimeBetween(String location, Date from, Date to);

}
