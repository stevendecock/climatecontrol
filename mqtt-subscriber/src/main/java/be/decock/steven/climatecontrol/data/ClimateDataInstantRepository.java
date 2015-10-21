package be.decock.steven.climatecontrol.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClimateDataInstantRepository extends MongoRepository<ClimateDataInstant, String> {

}
