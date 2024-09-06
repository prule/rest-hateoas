package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.querydsl.core.types.Predicate

fun interface Expression {
    fun build(): Predicate?
}
