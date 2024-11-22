package com.example.rest_hateoas.application.service.user

import com.example.rest_hateoas.application.port.`in`.usergroup.RemoveUserGroupCommand
import com.example.rest_hateoas.application.port.`in`.usergroup.RemoveGroupFromUserUseCase
import com.example.rest_hateoas.application.port.out.persistence.UserRepository

class RemoveGroupFromUserService(val userRepository: UserRepository): RemoveGroupFromUserUseCase {
    override fun removeGroup(command: RemoveUserGroupCommand) {
        // todo business logic here
        command.user.removeGroup(command.group)
        userRepository.save(command.user)
    }
}