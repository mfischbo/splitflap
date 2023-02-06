package net.fischboeck.splitflap.provider.bvg

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.service.annotation.GetExchange


interface BvgApiClient {

    @GetExchange("/stops/{stopId}/departures")
    fun getDepartures(@PathVariable("stopId") stopId: String): List<BvgDeparturesResponse>
}