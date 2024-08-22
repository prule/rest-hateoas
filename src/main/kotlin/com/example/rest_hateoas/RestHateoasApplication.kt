package com.example.rest_hateoas

import com.example.rest_hateoas.common.Loader
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener

@SpringBootApplication
class RestHateoasApplication(val sampleLoaders: List<Loader>) {

    @EventListener(ApplicationReadyEvent::class)
    fun postStartup() {
        sampleLoaders.forEach { it.load() }
    }

}

fun main(args: Array<String>) {
    runApplication<RestHateoasApplication>(*args)
}
