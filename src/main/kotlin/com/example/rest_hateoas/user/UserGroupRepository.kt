package com.example.rest_hateoas.user

import com.example.rest_hateoas.common.KeyedCrudRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserGroupRepository : KeyedCrudRepository<UserGroup?, Long?> {
    fun findAll(pageable: Pageable?): Page<User?>?
}
