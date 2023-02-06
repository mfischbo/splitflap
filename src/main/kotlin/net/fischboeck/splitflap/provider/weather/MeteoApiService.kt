package net.fischboeck.splitflap.provider.weather

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import net.fischboeck.splitflap.provider.MessageProvider
import net.fischboeck.splitflap.util.Alignment
import net.fischboeck.splitflap.util.DisplayFormatterBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MeteoApiService(
    @Value("\${provider.weather.city}")
    private val city: String,

    @Value("\${provider.weather.latitude}")
    private val latitude: Double,

    @Value("\${provider.weather.longitude}")
    private val longitude: Double,

    private val apiClient: MeteoApiClient,
): MessageProvider {


    override fun nextMessage(): String {

        val response = apiClient.getCurrentWeather(latitude, longitude)
        return DisplayFormatterBuilder.newBuilder(15, 4)
            .append(city.uppercase(), Alignment.LEFT)
            .append("${response.weather.temperature} CELSIUS", Alignment.LEFT)
            .append("", Alignment.LEFT)
            .append(WMODecoder.decode(response.weather.weathercode), Alignment.LEFT)
            .build()
    }
}