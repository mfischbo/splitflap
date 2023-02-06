package net.fischboeck.splitflap.provider.bvg

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.support.WebClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class BvgApiBeans(
    @Value("\${provider.bvg.baseUrl}")
    private val apiBaseUrl: String
) {

    @Bean
    fun bvgApiClient(): BvgApiClient {

        val client = WebClient.builder()
            .baseUrl(apiBaseUrl)
            .build()
        val proxyFactory = HttpServiceProxyFactory.builder(
            WebClientAdapter.forClient(client))
            .build()

        return proxyFactory.createClient(BvgApiClient::class.java)
    }
}