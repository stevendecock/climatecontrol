package be.decock.steven.climatecontrol.data;

import java.util.Date;

public interface IClimateDataPoint {

    float getRelativeHumidity();

    float getTemperature();

    Date getTime();

    float getAbsoluteHumidity();
}
