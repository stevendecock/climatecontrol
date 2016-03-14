package be.decock.steven.climatecontrol.data;

import org.springframework.stereotype.Component;

@Component
public class ClimateDataPointMapper {

    public ClimateDataPoint mapTo(ClimateDataInstant climateDataInstant) {
        return new ClimateDataPoint(climateDataInstant.getHumidity(), climateDataInstant.getTemperature(), climateDataInstant.getTime());
    }

}
