package com.example.rest_hateoas.application.port.out.persistence

import com.example.rest_hateoas.application.port.`in`.Filter
import com.example.rest_hateoas.application.port.`in`.usergroup.UserGroupFilter
import com.example.rest_hateoas.domain.Page
import com.example.rest_hateoas.domain.PageData
import com.example.rest_hateoas.domain.model.Key
import com.example.rest_hateoas.domain.model.UserGroup

interface UserGroupRepository : Repository<UserGroup, UserGroupFilter> {
}