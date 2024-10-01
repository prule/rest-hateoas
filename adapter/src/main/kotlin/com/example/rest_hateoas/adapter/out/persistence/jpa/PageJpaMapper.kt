package com.example.rest_hateoas.adapter.out.persistence.jpa

import com.example.rest_hateoas.domain.Order
import com.example.rest_hateoas.domain.Page
import com.example.rest_hateoas.domain.PageData
import com.example.rest_hateoas.domain.Sort
import org.springframework.data.domain.PageImpl

class PageJpaMapper {
    companion object {
        fun <T> toDomain(value: PageImpl<T>): PageData<T> {
            return PageData(
                value.content,
                value.numberOfElements.toLong(),
                Page(
                    value.number,
                    value.size,
                    SortJpaMapper.toDomain(value.sort)
                )
            )
        }

        fun toJpaEntity(value: Page?): org.springframework.data.domain.Pageable {
            if (value == null) {
                return org.springframework.data.domain.PageRequest.of(0, 10)
            }
            return org.springframework.data.domain.PageRequest.of(
                value.page,
                value.size,
                org.springframework.data.domain.Sort.by(value.sort.orders.map {
                    org.springframework.data.domain.Sort.Order(
                        org.springframework.data.domain.Sort.Direction.valueOf(it.direction), it.property
                    )
                }.toList())
            )
        }
    }
}

class SortJpaMapper {
    companion object {
        fun toDomain(value: org.springframework.data.domain.Sort): Sort {
            return Sort(value.map { Order(it.property, it.direction.name) }.toList())
        }
    }
}
