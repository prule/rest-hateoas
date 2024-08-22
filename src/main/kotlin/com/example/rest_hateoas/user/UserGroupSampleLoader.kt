package com.example.rest_hateoas.user

import com.example.rest_hateoas.common.Loader
import io.github.xn32.json5k.Json5
import io.github.xn32.json5k.decodeFromStream
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.util.function.Function

@Component
@Order(1)
class UserGroupSampleLoader(
    private val userGroupRepository: UserGroupRepository,
) : Loader {

    override fun load() {
        createOrUpdate("data/sample/userGroups.json5") { obj: UserGroup -> createOrUpdateUser(obj) }
    }

    private fun createOrUpdate(path: String, runnable: Function<UserGroup, UserGroup>) {
        // load object graph
        val objects = loadData(path)
        // create or update accordingly
        for (obj in objects) {
            runnable.apply(obj)
        }
    }

    private fun createOrUpdateUser(newUserGroup: UserGroup): UserGroup {
        val userGroup = userGroupRepository.findByKey(newUserGroup.key) ?: newUserGroup
        userGroup.name = newUserGroup.name
        userGroup.description = newUserGroup.description
        userGroup.enabled = newUserGroup.enabled
        return userGroupRepository.save(userGroup)
    }

    private fun loadData(path: String): Iterable<UserGroup> {
        val inputStream = this.javaClass.classLoader.getResourceAsStream(path)
            ?: throw IllegalArgumentException("Resource not found: $path")
        return Json5.decodeFromStream<List<UserGroup>>(inputStream)
    }
}