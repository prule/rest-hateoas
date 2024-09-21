package com.example.rest_hateoas.domain

open class Validator {
    val errors: MutableList<String> = mutableListOf()

    fun validate(path: String, checks: () -> Unit) {
        // todo handle path
        checks()
    }

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
            throw ModelValidationException(errors.joinToString("\n"))
        }
    }
}