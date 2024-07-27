package com.example.rest_hateoas.person

import com.example.rest_hateoas.common.PredicateBuilder
import com.querydsl.core.types.Predicate
import java.time.LocalDate

class PersonSearchCriteria(
    val filter: String? = null,
    val from: Int? = null,
    val to: Int? = null
) {

    fun toPredicate(): Predicate {
        val qPerson: QPerson = QPerson.person
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
