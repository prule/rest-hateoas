package com.example.rest_hateoas.adapter.out.persistence.jpa.sample.user

import com.example.rest_hateoas.adapter.out.persistence.jpa.UserGroupJpaEntity
import com.example.rest_hateoas.adapter.out.persistence.jpa.UserGroupMapper
import com.example.rest_hateoas.application.domain.model.UserGroup
import com.example.rest_hateoas.application.port.out.persistence.UserGroupRepository
import com.example.rest_hateoas.adapter.out.persistence.jpa.sample.common.Loader
import io.github.xn32.json5k.Json5
import io.github.xn32.json5k.decodeFromStream
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(1)
class UserGroupSampleLoader(
    private val userGroupRepository: UserGroupRepository,
) : Loader {

    override fun load() {
        // load object graph
        val objects = loadData("data/sample/userGroups.json5")
        // create or update accordingly
        for (obj in objects) {
            userGroupRepository.save(UserGroupMapper.toDomain(obj))
        }
    }

    private fun loadData(path: String): Iterable<UserGroupJpaEntity> {
        val inputStream = this.javaClass.classLoader.getResourceAsStream(path)
            ?: throw IllegalArgumentException("Resource not found: $path")
        return Json5.decodeFromStream<List<UserGroupJpaEntity>>(inputStream)
    }
}