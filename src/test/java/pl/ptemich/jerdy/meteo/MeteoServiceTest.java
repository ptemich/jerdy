package pl.ptemich.jerdy.meteo;

import org.junit.jupiter.api.Test;

public class MeteoServiceTest {

    @Test
    public void test() {
        MeteoService meteoService = new MeteoService();
        MeteoResponseDto meteoResponseDto = meteoService.loadForecast();
        System.out.println("tesst");
    }

}
