package be.decock.steven.climatecontrol.data;

import org.springframework.data.annotation.Id;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.HOURS;

public class ClimateDataHour implements IClimateDataPoint {

    @Id
    private String id;
    private String location;
    private float averageRelativeHumidity = 0f;
    private float averageAbsoluteHumidity = 0f;
    private float averageTemperature = 0f;
    private Date startTime;
    private List<ClimateDataPoint> points = new ArrayList<>();

    public ClimateDataHour() {
    }

    public ClimateDataHour(String location, LocalDateTime startTime) {
        this.location = location;
        this.startTime = toDate(startTime.truncatedTo(HOURS));
    }

    public String getLocation() {
        return location;
    }

    public Date getStartTime() {
        return startTime;
    }


    public float getAverageAbsoluteHumidity() {
        return averageAbsoluteHumidity;
    }

    public float getAverageRelativeHumidity() {
        return averageRelativeHumidity;
    }

    public float getAverageTemperature() {
        return averageTemperature;
    }

    public List<ClimateDataPoint> getPoints() {
        return Collections.unmodifiableList(points);
    }

    public void addPoint(ClimateDataPoint point) {
        points.add(point);
        this.averageTemperature = recalculateNewAverage(this.averageTemperature, points.size() - 1, point.getTemperature());
        this.averageAbsoluteHumidity = recalculateNewAverage(this.averageAbsoluteHumidity, points.size() - 1, point.getAbsoluteHumidity());
        this.averageRelativeHumidity = recalculateNewAverage(this.averageRelativeHumidity, points.size() - 1, point.getRelativeHumidity());
    }

    private float recalculateNewAverage(float oldAverage, int oldDataSetSize, float newValue) {
        float oldTotal = oldAverage * oldDataSetSize;
        return (oldTotal + newValue) / points.size();
    }

    public boolean containsTime(LocalDateTime dateTime) {
        return toLocalDateTime(startTime).until(dateTime, ChronoUnit.HOURS) < 1;
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    @Override
    public float getRelativeHumidity() {
        return getAverageRelativeHumidity();
    }

    @Override
    public float getTemperature() {
        return getAverageTemperature();
    }

    @Override
    public Date getTime() {
        return getStartTime();
    }

    @Override
    public float getAbsoluteHumidity() {
        return getAverageAbsoluteHumidity();
    }
}
