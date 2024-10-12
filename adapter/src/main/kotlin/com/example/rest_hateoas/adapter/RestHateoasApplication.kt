package com.example.rest_hateoas.adapter

import com.example.rest_hateoas.adapter.out.persistence.jpa.sample.common.Loader
import org.apache.commons.logging.LogFactory
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.runApplication
import org.springframework.context.event.EventListener
import java.util.*

@SpringBootApplication(scanBasePackages = [
    "com.example.rest_hateoas",
    "com.example.rest_hateoas.adapter",
    "com.example.rest_hateoas.application",
    "com.example.rest_hateoas.fixtures"
])
class RestHateoasApplication(val fixtureLoader: FixtureLoader) {
    protected val logger = LoggerFactory.getLogger(javaClass)

    @EventListener(ApplicationReadyEvent::class)
    fun postStartup() {
        // so that all rest responses are in UTC
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));

        logger.info("Data loading started")
        fixtureLoader.load()
        logger.info("Data loading finished")

    }

}
//
//fun main(args: Array<String>) {
//    runApplication<RestHateoasApplication>(*args)
//}
