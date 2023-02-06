package net.fischboeck.splitflap.provider.bvg

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

data class BvgDeparturesResponse(

    val tripId: String,
    val stop: BvgStop,

    @JsonProperty("when")
    val departureAt: ZonedDateTime,
    val plannedWhen: ZonedDateTime,
    val delay: Int,
    val direction: String,
    val line: BvgLine,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class BvgStop(
    val id: String,
    val name: String,
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class BvgLine(
    val name: String
)

