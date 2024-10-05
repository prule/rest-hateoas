package com.example.rest_hateoas.application.service.usergroup

import com.example.rest_hateoas.application.port.`in`.user.FindUserUseCase
import com.example.rest_hateoas.application.port.`in`.usergroup.FindUserGroupUseCase
import com.example.rest_hateoas.application.port.out.persistence.UserGroupRepository
import com.example.rest_hateoas.application.port.out.persistence.UserRepository
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.User
import com.example.rest_hateoas.domain.model.UserGroup
import org.springframework.stereotype.Service

@Service
class FindUserGroupService(val userGroupRepository: UserGroupRepository): FindUserGroupUseCase {
    override fun findByKey(key: Key): UserGroup? {
        return userGroupRepository.findByKey(key)
    }
}