package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.google.common.base.Strings
import com.querydsl.core.BooleanBuilder
import com.querydsl.core.types.Predicate

class PredicateBuilder {
    private val builder: BooleanBuilder = BooleanBuilder()

    fun and(include: Boolean, expression: Expression): PredicateBuilder {
        if (include) {
            builder.and(expression.build())
        }
        return this
    }

    fun and(value: String?, expression: Expression): PredicateBuilder {
        if (!Strings.isNullOrEmpty(value)) {
            builder.and(expression.build())
        }
        return this
    }

    fun toPredicate(): Predicate {
        return builder
    }
}
