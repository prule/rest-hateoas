package com.example.rest_hateoas.application.service.user

import com.example.rest_hateoas.application.port.`in`.usergroup.AddUserGroupCommand
import com.example.rest_hateoas.application.port.`in`.usergroup.AddUserGroupUseCase
import com.example.rest_hateoas.application.port.out.persistence.UserRepository
import org.springframework.stereotype.Service

@Service
class AddUserGroupService(val userRepository: UserRepository): AddUserGroupUseCase {
    override fun addGroup(command: AddUserGroupCommand) {
        command.user.addGroup(command.group)
        userRepository.save(command.user)
    }
}