package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.application.domain.model.Key
import com.example.rest_hateoas.application.port.`in`.PersonFindUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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

    @Operation(summary = "Find person by key", description = "Find person by key", tags = ["Person"])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Found Person",
                content = [(Content(mediaType = "application/json"))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Did not find person with given key", content = [Content()])]
    )
    @GetMapping("/api/1/persons/{key}")
    fun find(
        @PathVariable(required = true) key: String,
    ): ResponseEntity<PersonRestModel> {
        val value = personFindUseCase.find(Key(key))
        return ResponseEntity(
            personRestMapper.fromDomain(value),
            HttpStatus.OK
        )
    }


}
