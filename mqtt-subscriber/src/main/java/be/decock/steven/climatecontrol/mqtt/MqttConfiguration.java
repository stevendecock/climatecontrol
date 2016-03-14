package be.decock.steven.climatecontrol.mqtt;

import be.decock.steven.climatecontrol.data.*;
import be.decock.steven.climatecontrol.service.ClimateDataHourService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.support.GenericMessage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;

import static com.google.common.base.Throwables.propagate;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.HOURS;

@Configuration
public class MqttConfiguration {

    private static int counter = 0;

    @Autowired
    private ClimateDataInstantRepository climateDataInstantRepository;

    @Autowired
    private ClimateDataHourService climateDataHourService;

    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory() {
        DefaultMqttPahoClientFactory defaultMqttPahoClientFactory = new DefaultMqttPahoClientFactory();
        return defaultMqttPahoClientFactory;
    }

    @Bean
    public IntegrationFlow flows() {
        return IntegrationFlows
                .from(mqttMessageDrivenChannelAdapter())
                .handle(m -> handle((GenericMessage) m))
                .get();
    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    private void handle(GenericMessage m) {
        LocalDateTime now = LocalDateTime.now();

        String payload = (String) m.getPayload();

        ObjectMapper mapper = new ObjectMapper();

        try {
            ClimateDataInstant dataInstant = mapper.readValue(payload, ClimateDataInstant.class);
            dataInstant.setTime(toDate(now));

            climateDataHourService.handleClimateDataInstant(dataInstant);

            System.out.print("*");
            if (++counter > 10) {
                System.out.println();
                counter = 0;
            }

            climateDataInstantRepository.save(dataInstant);
        } catch (IOException e) {
            throw propagate(e);
        }
    }

    public MqttPahoMessageDrivenChannelAdapter mqttMessageDrivenChannelAdapter() {
        MqttPahoMessageDrivenChannelAdapter channelAdapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        "tcp://stevenyun.local:1883", "subscriber", mqttPahoClientFactory());
        channelAdapter.addTopic("/yun/out", 0);
        return channelAdapter;
    }

}
