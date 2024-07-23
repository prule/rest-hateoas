package com.example.rest_hateoas

import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {
    @GetMapping("/greeting")
    fun greeting(
        @RequestParam(value = "name", defaultValue = "World") name: String?
    ): HttpEntity<Greeting> {
        val greeting = Greeting(String.format(TEMPLATE, name))
        greeting.add(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(GreetingController::class.java).greeting(name))
                .withSelfRel()
        )

        return ResponseEntity(greeting, HttpStatus.OK)
    }

    companion object {
        private const val TEMPLATE = "Hello, %s!"
    }
}