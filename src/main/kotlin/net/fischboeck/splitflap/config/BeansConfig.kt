package net.fischboeck.splitflap.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.format.DateTimeFormatter

@Configuration
class BeansConfig {

    @Bean
    fun timeFormatter(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern("HH-mm")
    }

    @Bean
    fun dateFormatter(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern("")
    }
}