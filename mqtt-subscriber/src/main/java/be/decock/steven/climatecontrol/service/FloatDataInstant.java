package be.decock.steven.climatecontrol.service;

import java.util.Date;

public class FloatDataInstant {

    private Date time;
    private float data;

    public FloatDataInstant(Date time, float data) {
        this.time = time;
        this.data = data;
    }

    public Date getTime() {
        return time;
    }

    public float getData() {
        return data;
    }
}
