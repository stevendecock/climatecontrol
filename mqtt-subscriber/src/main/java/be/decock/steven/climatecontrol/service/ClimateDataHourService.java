package be.decock.steven.climatecontrol.service;

import be.decock.steven.climatecontrol.data.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class ClimateDataHourService implements ClimateDataPointService<ClimateDataHour> {

    @Autowired
    private ClimateDataHourRepository repository;

    @Autowired
    private ClimateDataPointMapper climateDataPointMapper;


    public void handleClimateDataInstant(ClimateDataInstant climateDataInstant) {
        LocalDateTime time = toLocalDateTime(climateDataInstant.getTime());
        ClimateDataPoint climateDataPoint = climateDataPointMapper.mapTo(climateDataInstant);

        ClimateDataHour lastClimateDataHour = repository.findFirstByLocationOrderByStartTimeDesc(climateDataInstant.getLocation());

        if (lastClimateDataHour == null || !lastClimateDataHour.containsTime(time)) {
            lastClimateDataHour = new ClimateDataHour(climateDataInstant.getLocation(), time);
        }
        lastClimateDataHour.addPoint(climateDataPoint);

        repository.save(lastClimateDataHour);
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    @Override
    public List<ClimateDataHour> findByLocation(String location) {
        return repository.findByLocationOrderByStartTimeAsc(location);
    }

    @Override
    public List<ClimateDataHour> findByLocationAndStartTimeAfter(String location, Date from) {
        return repository.findByLocationAndStartTimeAfterOrderByStartTimeAsc(location, from);
    }

    @Override
    public List<ClimateDataHour> findByLocationAndStartTimeBefore(String location, Date to) {
        return repository.findByLocationAndStartTimeBeforeOrderByStartTimeAsc(location, to);
    }

    @Override
    public List<ClimateDataHour> findByLocationAndStartTimeBetween(String location, Date from, Date to) {
        return repository.findByLocationAndStartTimeBetweenOrderByStartTimeAsc(location, from, to);
    }
}
