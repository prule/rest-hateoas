package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.domain.Page
import com.example.rest_hateoas.domain.PageData
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.User
import com.example.rest_hateoas.domain.model.UserGroup

interface UserGroupRepository {
    fun findOneByKey(key: Key): UserGroup
    fun findIfExists(key: Key): UserGroup?
    fun findByKey(key: Key): UserGroup?
    fun findAll(page: Page): PageData<UserGroup>
    fun save(userGroup: UserGroup)
    fun delete(key: Key)
}