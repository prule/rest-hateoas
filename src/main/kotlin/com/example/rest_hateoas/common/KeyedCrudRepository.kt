package com.example.rest_hateoas.common

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

@NoRepositoryBean
interface KeyedCrudRepository<T, ID> : CrudRepository<T, ID> {
    fun findByKey(key: Key?): Optional<T>?
}
