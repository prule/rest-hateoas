package com.example.rest_hateoas.application.port.`in`.usergroup

interface RemoveUserGroupUseCase {
    fun removeGroup(command: RemoveUserGroupCommand)
}