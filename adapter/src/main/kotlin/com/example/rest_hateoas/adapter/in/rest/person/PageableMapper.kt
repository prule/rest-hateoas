package com.example.rest_hateoas.adapter.`in`.rest.person

import com.example.rest_hateoas.domain.Order
import com.example.rest_hateoas.domain.Page
import com.example.rest_hateoas.domain.Sort
import org.springframework.data.domain.Pageable

class PageableMapper {
    companion object {
        fun toDomain(value: Pageable): Page {
            return Page(
                value.pageNumber,
                value.pageSize,
                Sort(
                    value.sort.toList().map {
                        Order(
                            it.property,
                            it.direction.name,
                        )
                    }
                ),
            )
        }

        fun toModel(value: Page): Pageable {
            return org.springframework.data.domain.PageRequest.of(
                value.page,
                value.size,
                org.springframework.data.domain.Sort.by(
                    value.sort.orders.map {
                        org.springframework.data.domain.Sort.Order(
                            org.springframework.data.domain.Sort.Direction.valueOf(it.direction),
                            it.property,
                        )
                    }
                )
            )
        }
    }
}