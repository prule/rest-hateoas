package com.example.rest_hateoas.adapter

import com.example.rest_hateoas.application.port.out.persistence.UserGroupRepository
import com.example.rest_hateoas.application.port.out.persistence.UserRepository
import com.example.rest_hateoas.application.service.user.AddUserGroupService
import com.example.rest_hateoas.application.service.user.DeleteUserGroupService
import com.example.rest_hateoas.application.service.usergroup.FindUserGroupService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringConfig(
    val userRepository: UserRepository,
    val userGroupRepository: UserGroupRepository
) {

    @Bean
    fun findUserGroupService(): FindUserGroupService {
        return FindUserGroupService(userGroupRepository)
    }

    @Bean
    fun addUserGroupService(): AddUserGroupService {
        return AddUserGroupService(userRepository)
    }

    @Bean
    fun deleteUserGroupService(): DeleteUserGroupService {
        return DeleteUserGroupService(userRepository)
    }

}