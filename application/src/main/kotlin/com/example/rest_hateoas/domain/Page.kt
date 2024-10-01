package com.example.rest_hateoas.domain

class Page(
    val page: Int = 0,
    val size: Int = 5,
    val sort: Sort = Sort(listOf())
) {
}