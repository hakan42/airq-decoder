package com.tandogan.airq.decoder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirQMessage
{
    @JsonProperty("DeviceID")
    private String deviceID;

    @JsonProperty("temperature")
    private List<Double> temperature = null;

    @JsonProperty("humidity")
    private List<Double> humidity = null;

    @JsonProperty("pressure")
    private List<Double> pressure = null;

    @JsonProperty("co2")
    private List<Double> co2 = null;

    @JsonProperty("oxygen")
    private List<Double> oxygen = null;

/*

    @JsonProperty("window_open")
    private Integer windowOpen;

    @JsonProperty("tvoc")
    private List<Double> tvoc = null;

    @JsonProperty("pm2_5")
    private List<Double> pm25 = null;

    @JsonProperty("window_event")
    private Integer windowEvent;

    @JsonProperty("measuretime")
    private Double measuretime;

    @JsonProperty("cnt0_3")
    private List<Double> cnt03 = null;

    @JsonProperty("Status")
    private Status status;

    @JsonProperty("sound")
    private List<Double> sound = null;

    @JsonProperty("performance")
    private Double performance;

    @JsonProperty("cnt0_5")
    private List<Double> cnt05 = null;

    @JsonProperty("uptime")
    private Integer uptime;

    @JsonProperty("humidity_abs")
    private List<Double> humidityAbs = null;

    @JsonProperty("health")
    private Double health;

    @JsonProperty("cnt2_5")
    private List<Double> cnt25 = null;

    @JsonProperty("person")
    private Integer person;

    @JsonProperty("cnt10")
    private List<Double> cnt10 = null;

    @JsonProperty("cnt5")
    private List<Double> cnt5 = null;

    @JsonProperty("timestamp")
    private Integer timestamp;

    @JsonProperty("TypPS")
    private Double typPS;

    @JsonProperty("dCO2dt")
    private Double dCO2dt;

    @JsonProperty("pm10")
    private List<Double> pm10 = null;

    @JsonProperty("bat")
    private List<Integer> bat = null;

    @JsonProperty("dewpt")
    private List<Double> dewpt = null;

    @JsonProperty("dHdt")
    private Double dHdt;

    @JsonProperty("sound_max")
    private Double soundMax;

    @JsonProperty("pm1")
    private List<Double> pm1 = null;

    @JsonProperty("door_event")
    private Double doorEvent;

    @JsonProperty("cnt1")
    private List<Double> cnt1 = null;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
 */
}
