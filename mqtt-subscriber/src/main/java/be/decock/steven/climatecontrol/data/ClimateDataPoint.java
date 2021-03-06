package be.decock.steven.climatecontrol.data;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class ClimateDataPoint implements IClimateDataPoint {

    private float relativeHumidity;
    private float absoluteHumidity;
    private float temperature;
    private Date time;

    public ClimateDataPoint() {
    }

    public ClimateDataPoint(float relativeHumidity, float temperature, Date time) {
        this.time = time;
        this.relativeHumidity = relativeHumidity;
        this.temperature = temperature;
        this.absoluteHumidity = calculateAbsoluteHumidityInGramsPerCubicMeter();
    }


    private float calculateAbsoluteHumidityInGramsPerCubicMeter() {
        return (float) (6.112 * Math.exp((17.67 * temperature) / (temperature + 243.5)) * relativeHumidity * 18.02 / ((273.15 + temperature) * 100 * 0.08314));
    }

    @Override
    public float getRelativeHumidity() {
        return relativeHumidity;
    }

    @Override
    public float getTemperature() {
        return temperature;
    }

    @Override
    public Date getTime() {
        return time;
    }

    @Override
    public float getAbsoluteHumidity() {
        return absoluteHumidity;
    }

}
