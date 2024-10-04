package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.adapter.`in`.rest.support.authentication.AuthenticatedUser
import com.example.rest_hateoas.application.port.`in`.user.FindUserUseCase
import com.example.rest_hateoas.domain.model.Key
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
@org.springframework.transaction.annotation.Transactional
class FindUserController(
    private val findUserUseCase: FindUserUseCase,
    private val userRestMapper: UserRestMapper
) {

    @GetMapping("/api/1/user/me")
    fun me(): ResponseEntity<UserRestModel> {
        AuthenticatedUser.instance?.let { user ->
            val value = findUserUseCase.findByUsername(user.principal.username)
            return ResponseEntity(
                userRestMapper.fromDomain(value),
                HttpStatus.OK
            )
        }
        throw RuntimeException("User not authenicated")
    }

    @GetMapping("/api/1/persons/{key}")
    fun find(
        @PathVariable(required = true) key: String? = null,
    ): ResponseEntity<UserRestModel> {
        findUserUseCase.findByKey(Key(key!!)).let { user ->
            return ResponseEntity(
                userRestMapper.fromDomain(user),
                HttpStatus.OK
            )
        }
    }

}
