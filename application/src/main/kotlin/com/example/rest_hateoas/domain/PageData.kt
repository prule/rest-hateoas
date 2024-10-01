package com.example.rest_hateoas.domain

class PageData<T>(
    val content: List<T>,
    val total: Long,
    val page: Page
) {
}