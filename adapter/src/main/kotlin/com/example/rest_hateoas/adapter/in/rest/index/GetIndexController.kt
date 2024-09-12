package com.example.rest_hateoas.adapter.`in`.rest.index

import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/1/index")
class GetIndexController() {
    @GetMapping
    fun index(): HttpEntity<IndexRestModel> {
        return ResponseEntity(IndexRestModel(), HttpStatus.OK)
    }

}