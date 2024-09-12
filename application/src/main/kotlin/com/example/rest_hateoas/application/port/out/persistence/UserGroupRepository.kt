package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.domain.model.UserGroup
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface UserGroupRepository {
    fun findAll(pageable: Pageable): Page<UserGroup>
    fun save(userGroup: UserGroup)
}