package net.fischboeck.splitflap.provider.weather

import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange

interface MeteoApiClient {

    @GetExchange("/v1/forecast?current_weather=true&hourly=temperature_2m&daily=weathercode,temperature_2m_max,temperature_2m_min,sunrise,sunset")
    fun getCurrentWeather(
        @RequestParam("latitude") latitude: Double,
        @RequestParam("longitude") longitude: Double,
        @RequestParam("timezone") timezone: String
    ): MeteoResponse

}