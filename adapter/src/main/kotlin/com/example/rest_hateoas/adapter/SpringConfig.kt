package com.example.rest_hateoas.adapter

import com.example.rest_hateoas.application.port.out.persistence.UserGroupRepository
import com.example.rest_hateoas.application.port.out.persistence.UserRepository
import com.example.rest_hateoas.application.service.user.AddGroupToUserService
import com.example.rest_hateoas.application.service.user.RemoveGroupFromUserService
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
    fun addUserGroupService(): AddGroupToUserService {
        return AddGroupToUserService(userRepository)
    }

    @Bean
    fun deleteUserGroupService(): RemoveGroupFromUserService {
        return RemoveGroupFromUserService(userRepository)
    }

}