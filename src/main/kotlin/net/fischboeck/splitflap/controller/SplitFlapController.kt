package net.fischboeck.splitflap.controller

import jakarta.servlet.http.HttpServletResponse
import net.fischboeck.splitflap.provider.bvg.BvgDepartureService
import net.fischboeck.splitflap.provider.clock.ClockService
import net.fischboeck.splitflap.provider.weather.MeteoApiService
import net.fischboeck.splitflap.util.DebugFormatter
import net.fischboeck.splitflap.util.ModeSelector
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SplitFlapController(
    private val debugFormatter: DebugFormatter,
    private val openWeatherApiService: MeteoApiService,
    private val clockService: ClockService,
    private val bvgService: BvgDepartureService
) {
    private var requests = 0

    @GetMapping("/index.txt", produces = [MediaType.TEXT_PLAIN_VALUE])
    fun getNextMessage(
        @RequestParam("display", defaultValue = "0") displayMode: Int,
        @RequestParam("debug", defaultValue = "false") debug: Boolean,
        response: HttpServletResponse): String {

        val mode = ModeSelector.of(displayMode)
        this.requests++



        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*")
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "GET")

        val result = when (requests % 3) {
            0 -> openWeatherApiService.nextMessage(mode)
            1 -> clockService.nextMessage(mode)
            2 -> bvgService.nextMessage(mode)
            else -> "CALCULATION!"
        }
        if (!debug) {
            return result
        } else {
            return debugFormatter.format(result, mode)
        }
    }
}