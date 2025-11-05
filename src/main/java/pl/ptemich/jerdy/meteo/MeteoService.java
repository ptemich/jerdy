package pl.ptemich.jerdy.meteo;

import org.springframework.web.client.RestClient;

public class MeteoService {

    public MeteoResponseDto loadForecast() {
        RestClient rc = RestClient
                .builder()
                .baseUrl("https://api.open-meteo.com")
                .build();

        return rc.get()
                .uri(uriBuilder ->
                    uriBuilder.path("/v1/forecast")
                            .queryParam("latitude", 52.52)
                            .queryParam("longitude",13.41)
                            .queryParam("hourly", "temperature_2m,wind_speed_10m,rain,wind_direction_10m,wind_gusts_10m")
                            .queryParam("forecast_days", 1)
                            .build()
                )
                .retrieve()
                .body(MeteoResponseDto.class);
    }

}
