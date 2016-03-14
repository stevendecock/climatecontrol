package be.decock.steven.climatecontrol.data;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class ClimateDataHourTest {

    @Test
    public void testContainsTime() {
        ClimateDataHour climateDataHour = new ClimateDataHour("basement", LocalDateTime.of(2016, Month.JANUARY, 5, 11, 0, 0));

        assertThat(climateDataHour.containsTime(LocalDateTime.of(2016, Month.JANUARY, 5, 11, 59, 59))).isTrue();
        assertThat(climateDataHour.containsTime(LocalDateTime.of(2016, Month.JANUARY, 5, 12, 0, 0))).isFalse();
    }

}