package net.fischboeck.splitflap.provider.weather

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class MeteoResponse(

    val latitude: Double,
    val longitude: Double,

    @JsonProperty("current_weather")
    val currentWeather: Weather,

    @JsonProperty("daily")
    val dailyWeather: WeatherForecast
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Weather(
    val temperature: Double,
    val windspeed: Double,
    val winddirection: Double,
    val weathercode: Int
)

data class WeatherForecast(

    val time: List<String>,
    val weathercode: List<Int>,
    @JsonProperty("temperature_2m_max")
    val tempMax: List<Float>,
    @JsonProperty("temperature_2m_min")
    val tempMin: List<Float>,
    val sunrise: List<String>,
    val sunset: List<String>
)