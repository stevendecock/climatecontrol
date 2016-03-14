package be.decock.steven.climatecontrol.data;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Date;

import static java.time.LocalDateTime.now;

public class ClimateDataInstant {

    @Id
    private String id;
    private String location;
    private float humidity;
    private double absoluteHumidity;
    private float temperature;
    private Date time;

    public ClimateDataInstant() {
    }

    public ClimateDataInstant(String location, float humidity, float absoluteHumidity, float temperature) {
        this.location = location;
        this.humidity = humidity;
        this.absoluteHumidity = absoluteHumidity;
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getTemperature() {
        return temperature;
    }

    public String getLocation() {
        return location;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setAbsoluteHumidity(double absoluteHumidity) {
        this.absoluteHumidity = absoluteHumidity;
    }

    public double getAbsoluteHumidity() {
        return absoluteHumidity;
    }

}
