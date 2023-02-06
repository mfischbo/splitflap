package net.fischboeck.splitflap.controller

import jakarta.servlet.http.HttpServletResponse
import net.fischboeck.splitflap.provider.bvg.BvgDepartureService
import net.fischboeck.splitflap.provider.clock.ClockService
import net.fischboeck.splitflap.provider.weather.MeteoApiService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SplitFlapController(
    private val openWeatherApiService: MeteoApiService,
    private val clockService: ClockService,
    private val bvgService: BvgDepartureService
) {
    private var requests = 0

    @GetMapping("/index.txt", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun getNextMessage(response: HttpServletResponse): String {
        this.requests++


        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET")

        /*
        if (requests % 2 == 0) {
            return openWeatherApiService.nextMessage()
        } else {
            return clockService.nextMessage()
        }*/
        return bvgService.nextMessage()
    }
}