package net.fischboeck.splitflap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
@EnableWebMvc
class SplitflapApplication

fun main(args: Array<String>) {
	runApplication<SplitflapApplication>(*args)
}
