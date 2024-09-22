package com.example.rest_hateoas.application.port

open class ValidatableCommand {
    val errors: MutableList<String> = mutableListOf()

    fun check(condition: Boolean, lazyMessage: () -> String) {
        if (!condition) {
            errors.add(lazyMessage())
        }
    }

    /**
     * Throws exception with the details of errors if present.
     */
    fun validate() {
        if (errors.isNotEmpty()) {
            throw CommandValidationException(errors.joinToString("\n"))
        }
    }
}