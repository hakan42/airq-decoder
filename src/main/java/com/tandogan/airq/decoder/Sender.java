package com.tandogan.airq.decoder;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class Sender
{
    @Value("${mqtt.target.server_uri}")
    String serverUri;

    @Value("${mqtt.target.username}")
    String username;

    @Value("${mqtt.target.password}")
    String password;

    @Value("${mqtt.target.topic_prefix}")
    String topicPrefix;

    @Value("${mqtt.target.publish_message}")
    boolean publishMessage = false;

    public void publish(String sensor, AirQMessage airQMessage)
    {
        if (publishMessage)
        {
            if (airQMessage != null)
            {
                String publisherId = UUID.randomUUID().toString();
                try
                {
                    IMqttClient publisher = new MqttClient(serverUri, publisherId);

                    MqttConnectOptions options = new MqttConnectOptions();
                    options.setAutomaticReconnect(true);
                    options.setCleanSession(true);
                    options.setUserName(username);
                    options.setPassword(password.toCharArray());
                    options.setConnectionTimeout(10);
                    publisher.connect(options);

                    internalSend(publisher, topicPrefix, sensor, "temperature", airQMessage.getTemperature());
                    internalSend(publisher, topicPrefix, sensor, "humidity", airQMessage.getHumidity());
                    internalSend(publisher, topicPrefix, sensor, "pressure", airQMessage.getPressure());

                    internalSend(publisher, topicPrefix, sensor, "co2", airQMessage.getCo2());
                    internalSend(publisher, topicPrefix, sensor, "oxygen", airQMessage.getOxygen());

                    publisher.disconnect();
                    publisher.close();
                }
                catch (MqttException e)
                {
                    log.error("while creating publisher", e);
                }
            }
        }
    }

    private void internalSend(IMqttClient publisher, String project, String sensor, String key, List<Double> data) throws MqttException
    {
        if (data != null)
        {
            if (data.size() > 0)
            {
                internalSend(publisher, project, sensor, key, data.get(0).toString());
            }
        }
    }

    private void internalSend(IMqttClient publisher, String project, String sensor, String key, String data) throws MqttException
    {
        byte[] payload = data.getBytes();

        MqttMessage message = new MqttMessage(payload);
        message.setQos(0);
        message.setRetained(true);

        String topic = project + "/" + sensor + "/" + key;
        topic = topic.toLowerCase();

        if (publishMessage)
        {
            log.info("internalSend: '{}' - '{}'", topic, data);
            publisher.publish(topic, message);
        }
    }
}
