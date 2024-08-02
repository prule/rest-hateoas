package com.example.rest_hateoas.person

import com.example.rest_hateoas.common.Key
import com.example.rest_hateoas.common.NotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.RepresentationModel
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("/api/1/persons")
class PersonController(private val repository: PersonRepository) {

    private val personModelAssembler = PersonModelAssembler()

    @GetMapping
    fun search(
        criteria: PersonSearchCriteria,
        pageable: Pageable,
        assembler: PagedResourcesAssembler<Person>?
    ): HttpEntity<PagedModel<PersonModel>> {
        val page = repository.findAll(criteria.toPredicate(), pageable)
        return ResponseEntity(
            assembler!!.toModel(page) { person: Person -> personModelAssembler.toModel(person) },
            HttpStatus.OK
        )
    }

    @GetMapping("/{key}")
    fun find(
        @PathVariable(name = "key") key: String,
    ): ResponseEntity<PersonModel> {
        val model = loadModel(key)
        return ResponseEntity.ok().body(personModelAssembler.toModel(model))
    }

    @PostMapping
    fun create(
        @RequestBody model: PersonModel,
    ): ResponseEntity<PersonModel> {
        // in a more complex application this would probably go through a service to create the new entity and apply business logic
        val entity = Person()
        personModelAssembler.toEntity(model, entity)
        val personModel = personModelAssembler.toModel(repository.save(entity))
        return ResponseEntity.created(personModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(personModel)
    }

    @PutMapping("/{key}")
    fun update(
        @PathVariable(name = "key") key: String,
        @RequestBody model: PersonModel
    ): ResponseEntity<PersonModel> {
        // in a more complex application this would probably go through a service to update the entity and apply business logic
        val entity = loadModel(key)
        personModelAssembler.toEntity(model, entity)
        val personModel = personModelAssembler.toModel(repository.save(entity))
        return ResponseEntity.created(personModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(personModel)
    }

    @DeleteMapping("/{key}")
    fun delete(
        @PathVariable(name = "key") key: String,
    ): ResponseEntity<PersonModel> {
        // in a more complex application this would probably go through a service to delete the entity and apply business logic
        repository.findOneByKey(Key(key));
        return ResponseEntity.noContent().build()
    }

    private fun loadModel(key: String): Person {
        return repository.findOneByKey(Key(key)).orElseThrow {
            NotFoundException(String.format("Person %s not found", key))
        }
    }

}
