package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.application.port.`in`.usergroup.AddUserGroupCommand
import com.example.rest_hateoas.application.port.`in`.user.FindUserUseCase
import com.example.rest_hateoas.application.port.`in`.usergroup.AddGroupToUserUseCase
import com.example.rest_hateoas.application.port.`in`.usergroup.FindUserGroupUseCase
import com.example.rest_hateoas.domain.model.Key
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class UserAddUserGroupController(
    private val manageUserGroupUseCase: AddGroupToUserUseCase,
    private val findUserUseCase: FindUserUseCase,
    private val findUserGroupUseCase: FindUserGroupUseCase,
    private val userRestMapper: UserRestMapper
) {

    companion object {
        const val ADD_GROUP_PATH = "/api/1/user/{userKey}/groups/{groupKey}"
    }

    @PutMapping(ADD_GROUP_PATH)
    fun addGroup(
        @PathVariable(required = true) userKey: String,
        @PathVariable(required = true) groupKey: String,
    ): UserRestModel {
        manageUserGroupUseCase.addGroup(
            AddUserGroupCommand(
                user = findUserUseCase.findByKey(Key(userKey)),
                group = findUserGroupUseCase.findByKey(Key(groupKey)),
            )
        )
        val user = findUserUseCase.findByKey(Key(userKey))
        return userRestMapper.fromDomain(user)
    }
}
