package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.adapter.out.persistence.jpa.QPersonJpaEntity
import com.example.rest_hateoas.common.PredicateBuilder
import com.querydsl.core.types.Predicate
import kotlinx.serialization.Serializable
import java.time.LocalDate

/**
 * Defines how Person can be searched. The logic here could be anything, but ultimately produces a Query-DSL predicate
 * which can be passed to a spring-data repository.
 */
@Serializable
class PersonSearchCriteria(
    val filter: String? = null,
    val from: Int? = null,
    val to: Int? = null
) {

    fun toPredicate(): Predicate {
        val qPerson: QPersonJpaEntity = QPersonJpaEntity.personJpaEntity
        val builder = PredicateBuilder()

        builder
            .and(filter) {
                qPerson.name.firstName.containsIgnoreCase(filter)
                    .or(qPerson.name.lastName.containsIgnoreCase(filter))
                    .or(qPerson.name.otherNames.containsIgnoreCase(filter))
            }
            .and(from != null) { qPerson.dateOfBirth.after(LocalDate.of(from!!, 1, 1)) }
            .and(to != null) { qPerson.dateOfBirth.before(LocalDate.of(to!! + 1, 1, 1)) }

        return builder.toPredicate()
    }
}
