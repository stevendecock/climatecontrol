package be.decock.steven.climatecontrol.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClimateDataInstantRepository extends MongoRepository<ClimateDataInstant, String> {

    List<ClimateDataInstant> findByLocation(String location);

}
