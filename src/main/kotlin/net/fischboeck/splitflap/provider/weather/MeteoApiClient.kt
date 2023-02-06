package net.fischboeck.splitflap.provider.weather

import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange

interface MeteoApiClient {

    @GetExchange("/v1/forecast?current_weather=true")
    fun getCurrentWeather(
        @RequestParam("latitude") latitude: Double,
        @RequestParam("longitude") longitude: Double
    ): MeteoResponse

}