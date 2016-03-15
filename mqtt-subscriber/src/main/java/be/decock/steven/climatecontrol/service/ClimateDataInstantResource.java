package be.decock.steven.climatecontrol.service;

import be.decock.steven.climatecontrol.data.*;
import be.decock.steven.climatecontrol.util.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
public class ClimateDataInstantResource {

    @Autowired
    private ClimateDataInstantService climateDataInstantService;

    @Autowired
    private ClimateDataHourService climateDataHourService;

    @Autowired
    private DateConverter dateConverter;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public List<ClimateDataInstant> alldata() {
        throw new UnsupportedOperationException("Too much data");
    }

    @RequestMapping(value = "/data/{location}", method = RequestMethod.GET)
    public List<ClimateDataInstant> alldataForLocation(@PathVariable("location") String location) {
        throw new UnsupportedOperationException("Too much data");
    }

    @RequestMapping(value = "/data/{location}/humidity", method = RequestMethod.GET)
    public List<Object[]> humidityForLocation(@PathVariable("location") String location,
                                              @RequestParam(value = "from", required = false) Long fromInMillis,
                                              @RequestParam(value = "to", required = false) Long toInMillis) {
        List<IClimateDataPoint> dataPoints = getClimateDataPoints(location, fromInMillis, toInMillis);

        return dataPoints
                .stream()
                .map(climateDataInstant -> {
                            Object[] arr = new Object[2];
                            arr[0] = climateDataInstant.getTime();
                            arr[1] = climateDataInstant.getRelativeHumidity();
                            return arr;
                        }
                )
                .collect(toList());
    }

    @RequestMapping(value = "/data/{location}/temperature", method = RequestMethod.GET)
    public List<Object[]> temperatureForLocation(@PathVariable("location") String location,
                                                 @RequestParam(value = "from", required = false) Long fromInMillis,
                                                 @RequestParam(value = "to", required = false) Long toInMillis) {
        List<IClimateDataPoint> dataPoints = getClimateDataPoints(location, fromInMillis, toInMillis);

        return dataPoints
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

    private List<IClimateDataPoint> getClimateDataPoints(@PathVariable("location") String location, @RequestParam(value = "from", required = false) Long fromInMillis, @RequestParam(value = "to", required = false) Long toInMillis) {
        Optional<LocalDateTime> fromDate = Optional.ofNullable(fromInMillis).map(millis -> dateConverter.toLocalDateTime(new Date(millis)));
        LocalDateTime toDate = Optional.ofNullable(toInMillis).map(millis -> dateConverter.toLocalDateTime(new Date(millis))).orElse(LocalDateTime.now());

        ClimateDataPointService climateDataPointService;

        if (isPeriodMoreThanNumberOfDays(4, toDate, fromDate)) {
            climateDataPointService = climateDataHourService;
        } else {
            climateDataPointService = climateDataInstantService;
        }

        List<IClimateDataPoint> dataPoints;
        if (fromDate.isPresent()) {
            dataPoints = climateDataPointService.findByLocationAndStartTimeBetween(location, dateConverter.toDate(fromDate.get()), dateConverter.toDate(toDate));
        } else if (fromDate.isPresent()) {
            dataPoints = climateDataPointService.findByLocationAndStartTimeAfter(location, dateConverter.toDate(fromDate.get()));
        } else {
            dataPoints = climateDataPointService.findByLocationAndStartTimeBefore(location, dateConverter.toDate(toDate));
        }
        return dataPoints;
    }

    private boolean isPeriodMoreThanNumberOfDays(int maxDaysForDetailedData, LocalDateTime toDate, Optional<LocalDateTime> fromDate) {
        boolean onlyHours = false;
        if (fromDate.isPresent()) {
            long numberOfDays = ChronoUnit.DAYS.between(fromDate.get(), toDate);
            if (numberOfDays > maxDaysForDetailedData) {
                onlyHours = true;
            }
        } else {
            onlyHours = true;
        }
        return onlyHours;
    }

}
