package com.example.rest_hateoas.common.errorhandling

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase
import jakarta.validation.ConstraintViolation
import org.hibernate.validator.internal.engine.path.PathImpl
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.util.*
import java.util.function.Consumer

@JsonTypeInfo(
    include = JsonTypeInfo.As.WRAPPER_OBJECT,
    use = JsonTypeInfo.Id.CUSTOM,
    property = "error",
    visible = true
)
@JsonTypeIdResolver(
    LowerCaseClassNameResolver::class
)
data class ApiError(
    val status: HttpStatus,
    val timestamp: ZonedDateTime? = ZonedDateTime.now(),
    val message: String? = null,
    val debugMessage: String? = null,
    val subErrors: List<ApiSubError>? = null
) {

    constructor(status: HttpStatus, ex: Throwable) : this(
        status,
        message = "Unexpected error",
        debugMessage = ex.localizedMessage
    )

    constructor(status: HttpStatus, message: String, ex: Throwable) : this(
        status,
        message = message,
        debugMessage = ex.localizedMessage
    )
//
//    private fun addSubError(subError: ApiSubError) {
//        if (subErrors == null) {
//            subErrors = ArrayList()
//        }
//        subErrors!!.add(subError)
//    }
//
//    private fun addValidationError(`object`: String, field: String, rejectedValue: Any, message: String) {
//        addSubError(ApiValidationError(`object`, field, rejectedValue, message))
//    }
//
//    private fun addValidationError(`object`: String, message: String) {
//        addSubError(ApiValidationError(`object`, message))
//    }
//
//    private fun addValidationError(fieldError: FieldError) {
//        this.addValidationError(
//            fieldError.getObjectName(),
//            fieldError.getField(),
//            fieldError.getRejectedValue(),
//            fieldError.getDefaultMessage()
//        )
//    }
//
//    fun addValidationErrors(fieldErrors: List<FieldError?>) {
//        fieldErrors.forEach(Consumer<FieldError> { fieldError: FieldError? -> this.addValidationError(fieldError) })
//    }
//
//    private fun addValidationError(objectError: ObjectError) {
//        this.addValidationError(
//            objectError.getObjectName(),
//            objectError.getDefaultMessage()
//        )
//    }
//
//    fun addValidationError(globalErrors: List<ObjectError?>) {
//        globalErrors.forEach(Consumer<ObjectError> { objectError: ObjectError? -> this.addValidationError(objectError) })
//    }
//
//    /**
//     * Utility method for adding error of ConstraintViolation. Usually when a @Validated validation fails.
//     *
//     * @param cv the ConstraintViolation
//     */
//    private fun addValidationError(cv: ConstraintViolation<*>) {
//        this.addValidationError(
//            cv.getRootBeanClass().getSimpleName(),
//            (cv.getPropertyPath() as PathImpl).leafNode.asString(),
//            cv.getInvalidValue(),
//            cv.getMessage()
//        )
//    }
//
//    fun addValidationErrors(constraintViolations: Set<ConstraintViolation<*>?>) {
//        constraintViolations.forEach(Consumer<ConstraintViolation<*>> { cv: ConstraintViolation<*>? ->
//            this.addValidationError(
//                cv
//            )
//        })
//    }


    abstract class ApiSubError()

    class ApiValidationError(private val `object`: String, private val message: String) : ApiSubError() {
        private val field: String? = null
        private val rejectedValue: Any? = null
    }
}

internal class LowerCaseClassNameResolver : TypeIdResolverBase() {
    override fun idFromValue(value: Any): String {
        return value.javaClass.simpleName.lowercase(Locale.getDefault())
    }

    override fun idFromValueAndType(value: Any, suggestedType: Class<*>?): String {
        return idFromValue(value)
    }

    override fun getMechanism(): JsonTypeInfo.Id {
        return JsonTypeInfo.Id.CUSTOM
    }
}