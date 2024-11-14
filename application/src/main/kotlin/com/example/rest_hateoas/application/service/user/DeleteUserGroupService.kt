package com.example.rest_hateoas.application.service.user

import com.example.rest_hateoas.application.port.`in`.usergroup.AddUserGroupCommand
import com.example.rest_hateoas.application.port.`in`.usergroup.AddUserGroupUseCase
import com.example.rest_hateoas.application.port.`in`.usergroup.RemoveUserGroupCommand
import com.example.rest_hateoas.application.port.`in`.usergroup.RemoveUserGroupUseCase
import com.example.rest_hateoas.application.port.out.persistence.UserRepository

class DeleteUserGroupService(val userRepository: UserRepository): RemoveUserGroupUseCase {
    override fun removeGroup(command: RemoveUserGroupCommand) {
        command.user.removeGroup(command.group)
        userRepository.save(command.user)
    }
}