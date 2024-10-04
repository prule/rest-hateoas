package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.application.port.`in`.user.AddUserGroupCommand
import com.example.rest_hateoas.application.port.`in`.user.FindUserUseCase
import com.example.rest_hateoas.application.port.`in`.user.ManageUserGroupUseCase
import com.example.rest_hateoas.application.port.`in`.usergroup.FindUserGroupUseCase
import com.example.rest_hateoas.domain.model.Key
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@org.springframework.transaction.annotation.Transactional
class ManageUserGroupsController(
    private val manageUserGroupUseCase: ManageUserGroupUseCase,
    private val findUserUseCase: FindUserUseCase,
    private val findUserGroupUseCase: FindUserGroupUseCase,
    private val userRestMapper: UserRestMapper
) {

    @PutMapping("/api/1/user/{userKey}/groups/{groupKey}")
    fun addGroup(
        @PathVariable(required = true) userKey: String,
        @PathVariable(required = true) groupKey: String,
    ): UserRestModel {
        manageUserGroupUseCase.addGroup(
            AddUserGroupCommand(
                user = findUserUseCase.findByKey(Key(userKey))!!,
                group = findUserGroupUseCase.findByKey(Key(groupKey))!!,
            )
        )
        val user = findUserUseCase.findByKey(Key(userKey))
        return userRestMapper.fromDomain(user)
    }
}
