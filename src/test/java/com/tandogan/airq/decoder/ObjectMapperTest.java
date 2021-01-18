package com.tandogan.airq.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Slf4j
public class ObjectMapperTest
{
    ObjectMapper objectMapper;

    String mqttMessage;

    @BeforeEach
    public void setUp() throws IOException, URISyntaxException
    {
        objectMapper = new ObjectMapper();

        Path path = Paths.get(getClass().getClassLoader().getResource("mqtt-message.json").toURI());

        Stream<String> lines = Files.lines(path);
        mqttMessage = lines.collect(Collectors.joining(System.lineSeparator()));
        lines.close();
    }

    @Test
    public void testReadValue() throws IOException
    {
        AirQMessage message = objectMapper.readValue(mqttMessage, AirQMessage.class);
        log.info("parsed thingy: {}", message);

        assertNotNull(message);

        assertNotNull(message.getTemperature());
        assertThat(message.getTemperature(), is(not(empty())));
        assertNotNull(message.getTemperature().get(0));

        assertNotNull(message.getHumidity());
        assertThat(message.getHumidity(), is(not(empty())));
        assertNotNull(message.getHumidity().get(0));

        assertNotNull(message.getPressure());
        assertThat(message.getPressure(), is(not(empty())));
        assertNotNull(message.getPressure().get(0));
    }
}
