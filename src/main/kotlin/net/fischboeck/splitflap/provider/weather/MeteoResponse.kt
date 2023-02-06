package net.fischboeck.splitflap.provider.weather

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MeteoResponse(

    val latitude: Double,
    val longitude: Double,

    @JsonProperty("current_weather")
    val weather: Weather
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Weather(
    val temperature: Double,
    val windspeed: Double,
    val winddirection: Double,
    val weathercode: Int
)