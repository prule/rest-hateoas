package com.example.rest_hateoas.application.service.user

import com.example.rest_hateoas.application.port.`in`.user.AddUserGroupCommand
import com.example.rest_hateoas.application.port.`in`.user.ManageUserGroupUseCase
import com.example.rest_hateoas.application.port.`in`.user.RemoveUserGroupCommand
import com.example.rest_hateoas.application.port.out.persistence.UserRepository

class ManageUserGroupService(val userRepository: UserRepository): ManageUserGroupUseCase {
    override fun addGroup(command: AddUserGroupCommand) {
        command.user.addGroup(command.group)
        userRepository.save(command.user)
    }


    override fun removeGroup(command: RemoveUserGroupCommand) {
        command.user.removeGroup(command.group)
        userRepository.save(command.user)
    }
}