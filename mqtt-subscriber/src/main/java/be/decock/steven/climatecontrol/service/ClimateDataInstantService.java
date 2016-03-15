package be.decock.steven.climatecontrol.service;

import be.decock.steven.climatecontrol.data.ClimateDataInstant;
import be.decock.steven.climatecontrol.data.ClimateDataInstantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ClimateDataInstantService implements ClimateDataPointService<ClimateDataInstant> {

    @Autowired
    private ClimateDataInstantRepository repository;

    @Override
    public List<ClimateDataInstant> findByLocation(String location) {
        return repository.findByLocation(location);
    }

    @Override
    public List<ClimateDataInstant> findByLocationAndStartTimeAfter(String location, Date from) {
        return repository.findByLocationAndTimeAfter(location, from);
    }

    @Override
    public List<ClimateDataInstant> findByLocationAndStartTimeBefore(String location, Date to) {
        return repository.findByLocationAndTimeBefore(location, to);
    }

    @Override
    public List<ClimateDataInstant> findByLocationAndStartTimeBetween(String location, Date from, Date to) {
        return repository.findByLocationAndTimeBetween(location, from, to);
    }

}
