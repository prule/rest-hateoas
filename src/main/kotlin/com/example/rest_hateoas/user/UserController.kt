package com.example.rest_hateoas.user

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@org.springframework.transaction.annotation.Transactional
class UserController(
    private val userRepository: UserRepository
) {

    @GetMapping("/api/1/user/me")
    fun me(): UserModel? {
        val user: AuthenticatedUser? = AuthenticatedUser.instance
        if (user != null) {
            return userRepository.findByUsername(user.principal.username).let { UserModel().fromModel(it!!) }
        }
        return null
    }
}
