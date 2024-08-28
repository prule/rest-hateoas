package com.example.rest_hateoas.person

import com.example.rest_hateoas.adapter.`in`.rest.person.PersonRestModel
import com.example.rest_hateoas.adapter.`in`.rest.person.PersonSearchCriteria
import com.example.rest_hateoas.adapter.out.persistence.jpa.PersonJpaEntity
import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.application.port.out.persistence.PersonRepository
import com.example.rest_hateoas.application.port.out.persistence.PersonSpringDataRepository
import com.example.rest_hateoas.common.Key
import com.example.rest_hateoas.common.NotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
@RequestMapping("/api/1/persons")
class PersonController(private val repository: PersonRepository) {

//
//    @GetMapping("/{key}")
//    fun find(
//        @PathVariable(required = true) key: String?,
//    ): ResponseEntity<PersonRestModel> {
//        val model = loadModel(key!!)
//        return ResponseEntity.ok().body(personModelAssembler.toModel(model))
//    }

//    @PostMapping
//    fun create(
//        @RequestBody model: PersonRestModel,
//    ): ResponseEntity<PersonRestModel> {
//        // in a more complex application this would probably go through a service to create the new entity and apply business logic
//        val entity = PersonJpaEntity(Key(model.key), model.name, model.address, model.dateOfBirth)
//        val personModel = personModelAssembler.toModel(repository.save(entity))
//        return ResponseEntity.created(personModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(personModel)
//    }
//
//    @PutMapping("/{key}")
//    fun update(
//        @PathVariable(name = "key", required = true) key: String?,
//        @RequestBody model: PersonRestModel
//    ): ResponseEntity<PersonRestModel> {
//        // in a more complex application this would probably go through a service to update the entity and apply business logic
//        val entity = loadModel(key!!)
//        personModelAssembler.toEntity(model, entity)
//        repository.save(entity)
//        val personModel = personModelAssembler.toModel(loadModel(key))
//        return ResponseEntity.created(personModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(personModel)
//    }
//
//    @DeleteMapping("/{key}")
//    fun delete(
//        @PathVariable(name = "key", required = true) key: String?,
//    ): ResponseEntity<PersonRestModel> {
//        // in a more complex application this would probably go through a service to delete the entity and apply business logic
//        repository.findOneByKey(Key(key!!)).ifPresent(repository::delete);
//        return ResponseEntity.noContent().build()
//    }
//
//    private fun loadModel(key: String): PersonJpaEntity {
//        return repository.findOneByKey(Key(key)).orElseThrow {
//            NotFoundException(String.format("Person %s not found", key))
//        }
//    }

}
