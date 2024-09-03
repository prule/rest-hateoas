package com.example.rest_hateoas.bootstrap

import com.example.rest_hateoas.adapter.RestHateoasApplication
import org.springframework.boot.SpringApplication

/**
 * Launcher for the application: starts the Spring application.
 *
 * @author Sven Woltmann
 */
object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(RestHateoasApplication::class.java, *args)
    }
}
