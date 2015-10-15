package be.decock.steven.mqtt.subscriber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.messaging.support.GenericMessage;

@SpringBootApplication
@IntegrationComponentScan
public class Application {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Hit 'Enter' to terminate");
        System.in.read();
        ctx.close();
    }

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
        System.out.println(m.getPayload());
    }

    public MqttPahoMessageDrivenChannelAdapter mqttMessageDrivenChannelAdapter() {
        MqttPahoMessageDrivenChannelAdapter channelAdapter = new MqttPahoMessageDrivenChannelAdapter("tcp://localhost:1883", "subscriber", mqttPahoClientFactory());
        channelAdapter.addTopic("demo/test", 0);
        return channelAdapter;
    }

}
