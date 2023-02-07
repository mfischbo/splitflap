package net.fischboeck.splitflap.provider.bvg

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class BvgConfig {

    @Bean
    fun bvgResponseCache(): Cache<String, List<BvgDeparturesResponse>> {
        return Caffeine.newBuilder()
            .maximumSize(1L)
            .expireAfterWrite(Duration.ofMinutes(1))
            .build()
    }
}