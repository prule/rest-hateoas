package com.example.rest_hateoas.application.port.`in`.usergroup

interface RemoveGroupFromUserUseCase {
    fun removeGroup(command: RemoveUserGroupCommand)
}