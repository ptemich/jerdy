package pl.ptemich.jerdy.meteo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HourlyUnits {
    private String time;

    @JsonProperty("temperature_2m")
    private String temperature2m;

    @JsonProperty("wind_speed_10m")
    private String windSpeed10m;

    private String rain;

    @JsonProperty("wind_direction_10m")
    private String windDirection10m;

    @JsonProperty("wind_gusts_10m")
    private String windGusts10m;

}

