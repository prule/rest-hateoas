package com.example.rest_hateoas.application.port.`in`.user

interface ManageUserGroupUseCase {
    fun addGroup(command: AddUserGroupCommand)
    fun removeGroup(command: RemoveUserGroupCommand)
}