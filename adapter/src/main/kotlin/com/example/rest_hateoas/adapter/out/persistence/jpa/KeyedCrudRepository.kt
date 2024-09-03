package com.example.rest_hateoas.adapter.out.persistence.jpa

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface KeyedCrudRepository<T, ID> : CrudRepository<T, ID> {
    fun findByKey(key: KeyJpaEntity?): T?
}
