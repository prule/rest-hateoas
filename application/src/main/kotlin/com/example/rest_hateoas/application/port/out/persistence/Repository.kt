package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.application.port.`in`.Filter
import com.example.rest_hateoas.domain.Page
import com.example.rest_hateoas.domain.PageData
import com.example.rest_hateoas.domain.model.Key

interface Repository<T, F: Filter> {
    fun findOneByKey(key: Key): T
    fun findIfExists(key: Key): T?
    fun findByKey(key: Key): T?
    fun findAll(filter: F, page: Page): PageData<T>
    fun delete(key: Key)
    fun save(value: T)
}