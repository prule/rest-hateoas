package com.example.rest_hateoas.index

import com.example.rest_hateoas.person.Person
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/1/index")
class IndexController() {
    @GetMapping
    fun index(assembler: PagedResourcesAssembler<Person>): HttpEntity<IndexResource> {
        return ResponseEntity(IndexResource(assembler), HttpStatus.OK)
    }

}