package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.application.port.`in`.person.PersonDeleteUseCase
import com.example.rest_hateoas.application.port.`in`.person.PersonFindUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
class PersonDeleteController(
    private val personDeleteUseCase: PersonDeleteUseCase,
    private val personFindUseCase: PersonFindUseCase,
    private val personRestMapper: PersonRestMapper
) {

    @Operation(summary = "Delete person by key", description = "Delete person by key", tags = ["Person"])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Delete Person",
                content = [(Content(mediaType = "application/json"))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Did not find person with given key", content = [Content()])]
    )
    @DeleteMapping("/api/1/persons/{key}")
    fun delete(
        @PathVariable(name = "key", required = true) key: String,
    ): ResponseEntity<Void> {
        personDeleteUseCase.delete(Key(key))
        return ResponseEntity.noContent().build()
    }


}
