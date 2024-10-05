package com.example.rest_hateoas.adapter.`in`.rest.user

import com.example.rest_hateoas.application.port.`in`.user.FindUserUseCase
import com.example.rest_hateoas.domain.model.Key
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@Transactional
class UserFindController(
    private val userFindUseCase: FindUserUseCase,
    private val userRestMapper: UserRestMapper
) {

    @GetMapping("/api/1/users/{key}")
    fun find(
        @PathVariable(required = true) key: String,
    ): ResponseEntity<UserRestModel> {
        val value = userFindUseCase.findByKey(Key(key))
        return ResponseEntity(
            userRestMapper.fromDomain(value),
            HttpStatus.OK
        )
    }


}
