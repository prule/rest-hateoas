package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.application.port.`in`.PersonCreateUseCase
import com.example.rest_hateoas.application.port.`in`.PersonFindUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
class PersonCreateController(
    private val personCreateUseCase: PersonCreateUseCase,
    private val personFindUseCase: PersonFindUseCase,
    private val personRestMapper: PersonRestMapper
) {

    @Operation(summary = "Create person", description = "Create person", tags = ["Person"])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Create Person",
                content = [(Content(mediaType = "application/json"))]
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])
        ]
    )
    @PostMapping("/api/1/persons")
    fun create(
        @Valid @RequestBody model: PersonCreateRestModel
    ): ResponseEntity<PersonRestModel> {
        val value = personRestMapper.toNewDomain(model)
        personCreateUseCase.create(value)
        val personModel = personRestMapper.fromDomain(
            personFindUseCase.find(value.key)
        )
        return ResponseEntity.created(personModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(personModel)
    }


}
