package com.example.rest_hateoas.application.port.`in`.usergroup

interface AddGroupToUserUseCase {
    fun addGroup(command: AddUserGroupCommand)
}