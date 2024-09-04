package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.application.domain.model.Key
import com.example.rest_hateoas.application.port.`in`.PersonFindUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
class PersonFindController(
    private val personFindUseCase: PersonFindUseCase,
    private val personRestMapper: PersonRestMapper
) {

    @GetMapping("/api/1/persons/{key}")
    fun find(
        @PathVariable(required = true) key: String?,
    ): ResponseEntity<PersonRestModel> {
        val value = personFindUseCase.find(Key(key!!))
        return ResponseEntity(
            personRestMapper.fromDomain(value),
            HttpStatus.OK
        )
    }


}
