package be.decock.steven.climatecontrol.service;

import be.decock.steven.climatecontrol.data.ClimateDataInstant;
import be.decock.steven.climatecontrol.data.ClimateDataInstantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@RestController
public class ClimateDataInstantResource {

    @Autowired
    private ClimateDataInstantRepository repository;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public List<ClimateDataInstant> alldata() {
        return repository.findAll();
    }

    @RequestMapping(value = "/data/{location}", method = RequestMethod.GET)
    public List<ClimateDataInstant> alldataForLocation(@PathVariable("location") String location) {
        return repository.findByLocation(location);
    }

    @RequestMapping(value = "/data/{location}/humidity", method = RequestMethod.GET)
    public List<Object[]> humidityForLocation(@PathVariable("location") String location,
                                              @RequestParam(value = "from", required = false) Long fromInMillis,
                                              @RequestParam(value = "to", required = false) Long toInMillis) {
        Optional<Date> fromDate = Optional.ofNullable(fromInMillis).map(millis -> new Date(millis));
        Optional<Date> toDate = Optional.ofNullable(toInMillis).map(millis -> new Date(millis));
        List<ClimateDataInstant> dataInstants;
        if (fromDate.isPresent() && toDate.isPresent()) {
            dataInstants = repository.findByLocationAndTimeBetween(location, fromDate.get(), toDate.get());
        } else if (fromDate.isPresent()) {
            dataInstants = repository.findByLocationAndTimeAfter(location, fromDate.get());
        } else if (toDate.isPresent()) {
            dataInstants = repository.findByLocationAndTimeBefore(location, toDate.get());
        } else {
            dataInstants = repository.findByLocation(location);
        }
        return dataInstants
                .stream()
                .map(climateDataInstant -> {
                            Object[] arr = new Object[2];
                            arr[0] = climateDataInstant.getTime();
                            arr[1] = climateDataInstant.getHumidity();
                            return arr;
                        }
                )
                .collect(toList());
    }

    @RequestMapping(value = "/data/{location}/temperature", method = RequestMethod.GET)
    public List<Object[]> temperatureForLocation(@PathVariable("location") String location,
                                                 @RequestParam(value = "from", required = false) Long fromInMillis,
                                                 @RequestParam(value = "to", required = false) Long toInMillis) {
        Optional<Date> fromDate = Optional.ofNullable(fromInMillis).map(millis -> new Date(millis));
        Optional<Date> toDate = Optional.ofNullable(toInMillis).map(millis -> new Date(millis));

        List<ClimateDataInstant> dataInstants;
        if (fromDate.isPresent() && toDate.isPresent()) {
            dataInstants = repository.findByLocationAndTimeBetween(location, fromDate.get(), toDate.get());
        } else if (fromDate.isPresent()) {
            dataInstants = repository.findByLocationAndTimeAfter(location, fromDate.get());
        } else if (toDate.isPresent()) {
            dataInstants = repository.findByLocationAndTimeBefore(location, toDate.get());
        } else {
            dataInstants = repository.findByLocation(location);
        }
        return dataInstants
                .stream()
                .map(climateDataInstant -> {
                            Object[] arr = new Object[2];
                            arr[0] = climateDataInstant.getTime();
                            arr[1] = climateDataInstant.getTemperature();
                            return arr;
                        }
                )
                .collect(toList());
    }

}
