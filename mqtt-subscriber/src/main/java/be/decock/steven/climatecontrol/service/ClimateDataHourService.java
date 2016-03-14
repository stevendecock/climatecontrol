package be.decock.steven.climatecontrol.service;

import be.decock.steven.climatecontrol.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClimateDataHourService {

    @Autowired
    private ClimateDataHourRepository climateDataHourRepository;

    @Autowired
    private ClimateDataPointMapper climateDataPointMapper;


    public void handleClimateDataInstant(ClimateDataInstant climateDataInstant) {
        LocalDateTime now = LocalDateTime.now();
        ClimateDataPoint climateDataPoint = climateDataPointMapper.mapTo(climateDataInstant);

        ClimateDataHour lastClimateDataHour = climateDataHourRepository.findFirstByLocationOrderByStartTimeDesc(climateDataInstant.getLocation());

        if (lastClimateDataHour == null || !lastClimateDataHour.containsTime(now)) {
            lastClimateDataHour = new ClimateDataHour(climateDataInstant.getLocation(), now);
        }
        lastClimateDataHour.addPoint(climateDataPoint);

        climateDataHourRepository.save(lastClimateDataHour);
    }

}
