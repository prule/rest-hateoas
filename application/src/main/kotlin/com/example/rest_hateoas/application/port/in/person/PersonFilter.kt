package com.example.rest_hateoas.application.port.`in`.person

import com.example.rest_hateoas.application.port.`in`.Filter
import com.example.rest_hateoas.domain.Page
import com.example.rest_hateoas.domain.Sort

class PersonFilter(
    val filter: String? = null,
    val from: Int? = null,
    val to: Int? = null,
    val page: Page = Page(0, 20, Sort(listOf()))
) : Filter