package com.example.rest_hateoas.common

import org.springframework.data.web.SortHandlerMethodArgumentResolverSupport

class SortHandler: SortHandlerMethodArgumentResolverSupport() {
    fun from(strings: Array<String>) {
//        parseParameterIntoSort()
    }
}