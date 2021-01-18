package com.tandogan.airq.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class Receiver implements CommandLineRunner, MqttCallback
{
    @Value("${mqtt.source.server_uri}")
    String serverUri;

    @Value("${mqtt.source.username}")
    String username;

    @Value("${mqtt.source.password}")
    String password;

    @Value("${mqtt.source.topic_filter}")
    String topicFilter;

    @Value("${mqtt.source.publish_decoded}")
    boolean publishDecoded = false;

    private MqttClient client;

    private ObjectMapper objectMapper = new ObjectMapper();

    public void run(String... args) throws Exception
    {
        log.info("Asking receiver to subscribe...");
        subscribe();
    }

    public void subscribe()
    {
        log.info("subscribe called...");

        try
        {
            String clientId = UUID.randomUUID().toString();

            client = new MqttClient(serverUri, clientId, new MemoryPersistence());

            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(42);

            client.setCallback(this);
            client.connect(options);
            client.subscribe(topicFilter);
        }
        catch (MqttException e)
        {
            log.error("while subscribing", e);
        }
    }

    @Override
    public void connectionLost(Throwable throwable)
    {
        log.info("connectionLost", throwable);
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception
    {
        log.info("messageArrived: '{}'", s);
        log.info("    message:    '{}'", new String(mqttMessage.getPayload()));

        try
        {
            AirQMessage airQMessage = objectMapper.readValue(new String(mqttMessage.getPayload()), AirQMessage.class);
            log.info("    decoded:    '{}'", airQMessage);
        }
        catch (Exception e)
        {
            log.error("While decoding message", e);
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken)
    {
        log.info("deliveryComplete: '{}'", iMqttDeliveryToken.isComplete());
    }
}
