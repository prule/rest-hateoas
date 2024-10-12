package com.example.rest_hateoas.application.port.`in`.usergroup

import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.User
import com.example.rest_hateoas.domain.model.UserGroup

interface FindUserGroupUseCase {
    fun findByKey(key: Key): UserGroup
}