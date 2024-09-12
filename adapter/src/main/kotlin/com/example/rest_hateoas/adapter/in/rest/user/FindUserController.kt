package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.adapter.`in`.rest.support.authentication.AuthenticatedUser
import com.example.rest_hateoas.application.port.`in`.FindUserUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@org.springframework.transaction.annotation.Transactional
class FindUserController(
    private val findUserUseCase: FindUserUseCase
) {

    @GetMapping("/api/1/user/me")
    fun me(): UserRestModel? {
        val user: AuthenticatedUser? = AuthenticatedUser.instance
        if (user != null) {
            return findUserUseCase.findByUsername(user.principal.username).let { UserRestModel().fromModel(it!!) }
        }
        return null
    }
}
