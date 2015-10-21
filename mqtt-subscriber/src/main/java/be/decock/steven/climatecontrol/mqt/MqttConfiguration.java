package be.decock.steven.climatecontrol.mqt;

import be.decock.steven.climatecontrol.data.ClimateDataInstant;
import be.decock.steven.climatecontrol.data.ClimateDataInstantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.support.GenericMessage;
import sun.jvm.hotspot.runtime.ObjectMonitor;

import java.io.IOException;

import static com.google.common.base.Throwables.propagate;

@Configuration
public class MqttConfiguration {

    @Autowired
    private ClimateDataInstantRepository climateDataInstantRepository;

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

    private void handle(GenericMessage m) {
        String payload = (String) m.getPayload();

        System.out.println(payload);

        ObjectMapper mapper = new ObjectMapper();

        try {
            ClimateDataInstant dataInstant = mapper.readValue(payload, ClimateDataInstant.class);

            climateDataInstantRepository.save(dataInstant);

        } catch (IOException e) {
            throw propagate(e);
        }
    }

    public MqttPahoMessageDrivenChannelAdapter mqttMessageDrivenChannelAdapter() {
        MqttPahoMessageDrivenChannelAdapter channelAdapter =
                new MqttPahoMessageDrivenChannelAdapter(
                        "tcp://localhost:1883", "subscriber", mqttPahoClientFactory());
        channelAdapter.addTopic("climate/basement", 0);
        return channelAdapter;
    }

}
