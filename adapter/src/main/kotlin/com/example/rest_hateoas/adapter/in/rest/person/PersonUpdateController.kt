package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.application.port.`in`.PersonFindUseCase
import com.example.rest_hateoas.application.port.`in`.PersonUpdateUseCase
import com.example.rest_hateoas.application.domain.model.Key
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PersonUpdateController(
    private val personUpdateUseCase: PersonUpdateUseCase,
    private val personFindUseCase: PersonFindUseCase,
    private val personRestMapper: PersonRestMapper
) {

    @Operation(summary = "Update person by key", description = "Update person by key", tags = ["Person"])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Update Person",
                content = [(Content(mediaType = "application/json"))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()]),
            ApiResponse(responseCode = "404", description = "Did not find person with given key", content = [Content()])]
    )
    @PutMapping("/api/1/persons/{key}")
    fun update(
        @PathVariable(name = "key", required = true) key: String,
        @Valid @RequestBody model: PersonUpdateRestModel
    ): ResponseEntity<PersonRestModel> {
        val value = personRestMapper.toExistingDomain(key, model)
        personUpdateUseCase.update(value)
        val personModel = personRestMapper.fromDomain(
            personFindUseCase.find(Key(key))
        )
        return ResponseEntity.created(personModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(personModel)
    }


}
