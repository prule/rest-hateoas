package com.example.rest_hateoas

import com.example.rest_hateoas.data.SampleLoader
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener

@SpringBootApplication
class RestHateoasApplication(val sampleLoader: SampleLoader) {

    @EventListener(ApplicationReadyEvent::class)
    fun postStartup() {
        sampleLoader.load()
    }

}

fun main(args: Array<String>) {
    runApplication<RestHateoasApplication>(*args)
}
