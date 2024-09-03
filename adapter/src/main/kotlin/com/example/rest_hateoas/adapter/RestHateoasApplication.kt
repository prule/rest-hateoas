package com.example.rest_hateoas.adapter

import com.example.rest_hateoas.adapter.out.persistence.jpa.sample.common.Loader
import org.apache.commons.logging.LogFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener

@SpringBootApplication(scanBasePackages = [
    "com.example.rest_hateoas.adapter",
    "com.example.rest_hateoas.application"
])
class RestHateoasApplication(val sampleLoaders: List<Loader>) {
    protected val logger = LogFactory.getLog(javaClass)

    @EventListener(ApplicationReadyEvent::class)
    fun postStartup() {
        logger.info("Data loading started")
        sampleLoaders.forEach { it.load() }
        logger.info("Data loading finished")
    }

}

fun main(args: Array<String>) {
    runApplication<RestHateoasApplication>(*args)
}
