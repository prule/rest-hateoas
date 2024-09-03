package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.application.port.`in`.PersonCreateUseCase
import com.example.rest_hateoas.application.port.`in`.PersonFindUseCase
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

    @PostMapping("/api/1/persons")
    fun create(
        @RequestBody model: PersonCreateRestModel
    ): ResponseEntity<PersonRestModel> {
        val value = personRestMapper.toNewDomain(model)
        personCreateUseCase.create(value)
        val personModel = personRestMapper.fromDomain(
            personFindUseCase.find(value.key)
        )
        return ResponseEntity.created(personModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(personModel)
    }


}
