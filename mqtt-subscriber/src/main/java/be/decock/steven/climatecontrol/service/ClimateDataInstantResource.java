package be.decock.steven.climatecontrol.service;

import be.decock.steven.climatecontrol.data.ClimateDataInstant;
import be.decock.steven.climatecontrol.data.ClimateDataInstantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
    public List<Object[]> humidityForLocation(@PathVariable("location") String location) {
        return repository.findByLocation(location)
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
    public List<Object[]> temperatureForLocation(@PathVariable("location") String location) {
        return repository.findByLocation(location)
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

    public ClimateDataInstantResource() {
        System.out.println("created resource");
    }
}
