package com.example.rest_hateoas.application.service.user

import com.example.rest_hateoas.application.port.`in`.usergroup.AddGroupToUserUseCase
import com.example.rest_hateoas.application.port.`in`.usergroup.AddUserGroupCommand
import com.example.rest_hateoas.application.port.out.persistence.UserRepository

class AddGroupToUserService(val userRepository: UserRepository) : AddGroupToUserUseCase {
    override fun addGroup(command: AddUserGroupCommand) {

        // todo add business logic here

        val user = command.user
        val group = command.group
        user.addGroup(group)
        userRepository.save(user)
    }
}