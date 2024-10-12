package com.example.rest_hateoas.application.port.`in`.usergroup

interface AddUserGroupUseCase {
    fun addGroup(command: AddUserGroupCommand)
}