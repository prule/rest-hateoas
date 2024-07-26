package com.example.rest_hateoas.common

import com.querydsl.core.types.Predicate

fun interface Expression {
    fun build(): Predicate?
}
