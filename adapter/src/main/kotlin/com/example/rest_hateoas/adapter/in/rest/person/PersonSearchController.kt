package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.application.domain.model.Person
import com.example.rest_hateoas.application.port.`in`.PersonSearchCriteria
import com.example.rest_hateoas.application.port.`in`.PersonSearchUseCase
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@Transactional
class PersonSearchController(
    private val personSearchUseCase: PersonSearchUseCase,
    private val personRestMapper: PersonRestMapper
) {

    // Sort parameters are not templated:
    //    https://github.com/spring-projects/spring-hateoas/issues/706
    //    https://github.com/spring-projects/spring-hateoas/pull/1312
    // At the moment the search href is not templated:
    //    http://localhost:8080/api/1/persons
    // It should be templated:
    //    http://localhost:8080/api/1/persons{?filter,from,to,page,size,sort}
    // But I haven't found a reasonable way to do this.
    @GetMapping("/api/1/persons")
    fun search(
        criteria: PersonSearchCriteriaRestModel?,
        pageable: Pageable?,
        assembler: PagedResourcesAssembler<Person>?
    ): HttpEntity<PagedModel<PersonRestModel>> {
        val page = personSearchUseCase.search(PersonSearchCriteria(criteria!!.filter, criteria.from, criteria.to), pageable!!)
        return ResponseEntity(
            assembler!!.toModel(page) { person: Person -> personRestMapper.fromDomain(person) },
            HttpStatus.OK
        )
    }

}
