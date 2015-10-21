package be.decock.steven.climatecontrol.data;

import org.springframework.data.annotation.Id;

public class ClimateDataInstant {

    @Id
    private String id;
    private float humidity;
    private float temperature;

    public ClimateDataInstant() {
    }

    public ClimateDataInstant(float humidity, float temperature) {
        this.humidity = humidity;
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getTemperature() {
        return temperature;
    }

}
