package be.decock.steven.mqtt.publish;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class Publisher {

    MqttClient mqttClient;

    public static void main(String[] args) {
        new Publisher().start();
    }

    public void start() {
        try {
            mqttClient = new MqttClient("tcp://localhost:1883", "mqttpublisher1");
            mqttClient.connect();
            MqttMessage message = new MqttMessage();
            message.setPayload("A single message".getBytes());
            mqttClient.publish("demo/test", message);
            mqttClient.disconnect();
        } catch(MqttException e) {
            e.printStackTrace();
        }
    }
}
