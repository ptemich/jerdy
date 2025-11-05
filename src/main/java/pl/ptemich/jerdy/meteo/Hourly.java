package pl.ptemich.jerdy.meteo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Hourly {

    private List<String> time;

    @JsonProperty("temperature_2m")
    private List<Double> temperature2m;

    @JsonProperty("wind_speed_10m")
    private List<Double> windSpeed10m;

    private List<Double> rain;

    @JsonProperty("wind_direction_10m")
    private List<Integer> windDirection10m;

    @JsonProperty("wind_gusts_10m")
    private List<Double> windGusts10m;

}

