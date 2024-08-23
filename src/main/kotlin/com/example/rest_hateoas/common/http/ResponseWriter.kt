package com.example.rest_hateoas.common.http

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.ResponseEntity

class ResponseWriter(val objectMapper: ObjectMapper) {
    fun write(httpServletResponse: HttpServletResponse, status: Int, responseEntity: ResponseEntity<*>) {
        httpServletResponse.setStatus(status)
        httpServletResponse.contentType = "application/hal+json"
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(responseEntity.body))
    }
}