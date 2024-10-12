package com.example.rest_hateoas

import com.example.rest_hateoas.adapter.RestHateoasApplication
import org.springframework.boot.SpringApplication

object Launcher {
    @JvmStatic
    fun main(args: Array<String>) {
        SpringApplication.run(RestHateoasApplication::class.java, *args)
    }
}
