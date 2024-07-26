package com.example.rest_hateoas.person

import com.example.rest_hateoas.common.Fields
import com.example.rest_hateoas.common.Key
import com.example.rest_hateoas.common.NotFoundException
import kotlinx.serialization.Serializable
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.function.Supplier

@Serializable
@RestController
@Transactional
@RequestMapping("/api/1/persons")
class PersonApi(private val repository: PersonRepository) {

    @GetMapping
    fun search(
        criteria: PersonSearchCriteria,
        fields: Fields,
        pageable: Pageable,
        assembler: PagedResourcesAssembler<Person>
    ): HttpEntity<PagedModel<PersonResource>> {
        val page = repository.findAll(criteria.toPredicate(), pageable)
        return ResponseEntity(assembler.toModel(page) { person: Person -> PersonResource().fromModel(person, fields) }, HttpStatus.OK)
    }

    @GetMapping("/{key}")
    fun find(@PathVariable key: String, fields: Fields): PersonResource {
        val model = loadModel(key)
        return PersonResource().fromModel(model, fields)
    }

    @PostMapping
    fun create(@RequestBody resource: PersonResource, fields: Fields): PersonResource {
        val model = Person()
        resource.toModel(model)
        repository.save(model)
        return find(model.key.key, fields)
    }

    @PutMapping("/{key}")
    fun update(@PathVariable key: String, fields: Fields, @RequestBody resource: PersonResource): PersonResource {
        val model = loadModel(key)
        resource.toModel(model)
        repository.save(model)
        return find(model.key.key, fields)
    }

    private fun loadModel(key: String): Person {
        return repository.findOneByKey(Key(key)).orElseThrow<RuntimeException>(Supplier<RuntimeException> {
            NotFoundException(String.format("Person %s not found", key))
        })
    }
}
