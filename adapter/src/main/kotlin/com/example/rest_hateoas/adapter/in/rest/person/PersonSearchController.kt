package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.application.port.`in`.person.PersonFilter
import com.example.rest_hateoas.application.port.`in`.person.PersonSearchUseCase
import com.example.rest_hateoas.domain.model.Person
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

// tag::PersonSearchController[]
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
    @Operation(summary = "Search person", description = "Search person", tags = ["Person"])
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Search Person",
                content = [(Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = PersonPage::class)
                ))]
//                content = [(Content(mediaType = "application/json", schema = Schema(implementation = PagedModel::class)))] <-- how to provide the "PersonRestModel" type for PagedModel<PersonRestModel> ??
            ),
            ApiResponse(responseCode = "400", description = "Bad request", content = [Content()])
        ]
    )
    @GetMapping("/api/1/persons")
    fun search(
        criteria: PersonSearchCriteriaRestModel?,
        pageable: Pageable = Pageable.unpaged(),
        assembler: PagedResourcesAssembler<Person>? = null
    ): HttpEntity<PersonPage> {
        val pageData = personSearchUseCase.search(
            PersonFilter(
                criteria!!.filter,
                criteria.from,
                criteria.to,
                PageableMapper.toDomain(pageable)
            )
        )
        return ResponseEntity(
            PersonPage(
                assembler!!.toModel(
                    PageImpl(
                        pageData.content,
                        PageableMapper.toModel(pageData.page),
                        pageData.total
                    )
                ) { person: Person -> personRestMapper.fromDomain(person) }),
            HttpStatus.OK
        )
    }

    // workaround to provide the right typing for swagger documentation
    // https://github.com/swagger-api/swagger-core/issues/3723
    // todo find a better solution for supplying the right typing for swagger documentation wrt pages
    class PersonPage(pageable: PagedModel<PersonRestModel>) :
        PagedModel<PersonRestModel>(pageable.content, pageable.metadata, pageable.links)

}
// end::PersonSearchController[]
