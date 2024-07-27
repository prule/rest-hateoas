package com.example.rest_hateoas.errorhandling

import com.example.rest_hateoas.common.NotFoundException
import jakarta.persistence.EntityNotFoundException
import org.hibernate.dialect.lock.OptimisticEntityLockException
import org.hibernate.exception.ConstraintViolationException
import org.hibernate.validator.internal.engine.path.PathImpl
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.function.Consumer

@Order(Ordered.LOWEST_PRECEDENCE)
@ControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {
    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex      MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    protected fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders?,
        status: HttpStatus?,
        request: WebRequest?
    ): ResponseEntity<Any> {
        val error: String = ex.getParameterName() + " parameter is missing"
        return buildResponseEntity(ApiError(HttpStatus.BAD_REQUEST, error, ex))
    }


    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex      HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    protected fun handleHttpMediaTypeNotSupported(
        ex: HttpMediaTypeNotSupportedException,
        headers: HttpHeaders?,
        status: HttpStatus?,
        request: WebRequest?
    ): ResponseEntity<Any> {
        val builder = StringBuilder()
        builder.append(ex.getContentType())
        builder.append(" media type is not supported. Supported media types are ")
        ex.getSupportedMediaTypes().forEach(Consumer { t: MediaType? -> builder.append(t).append(", ") })
        return buildResponseEntity(
            ApiError(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                builder.substring(0, builder.length - 2),
                ex
            )
        )
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex      the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    protected fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders?,
        status: HttpStatus?,
        request: WebRequest?
    ): ResponseEntity<Any> {
        val apiError = ApiError(
            HttpStatus.BAD_REQUEST,
            message = "Validation error",
            subErrors = ex.bindingResult.fieldErrors.map { cv ->
                ApiValidationError(
                    cv.objectName,
                    cv.field,
                    cv.rejectedValue,
                    cv.defaultMessage ?: "Unknown reason"
                )
            }.plus(
                ex.bindingResult.globalErrors.map { gv ->
                    ApiValidationError(
                        gv.objectName,
                        message = gv.defaultMessage ?: "Unknown reason"
                    )
                }
            )
        )
        return buildResponseEntity(apiError)
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(jakarta.validation.ConstraintViolationException::class)
    protected fun handleConstraintViolation(ex: jakarta.validation.ConstraintViolationException): ResponseEntity<Any> {
        val apiError = ApiError(
            HttpStatus.BAD_REQUEST,
            message = "Validation error",
            subErrors = ex.constraintViolations.map { cv ->
                ApiValidationError(
                    cv.rootBeanClass.getSimpleName(),
                    (cv.propertyPath as PathImpl).leafNode.asString(),
                    cv.invalidValue,
                    cv.message
                )
            })
        return buildResponseEntity(apiError)
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiError object
     */
    @ExceptionHandler(NotFoundException::class)
    protected fun handleEntityNotFound(ex: NotFoundException): ResponseEntity<Any> {
        val apiError = ApiError(HttpStatus.NOT_FOUND, message = "Not Found", debugMessage = ex.message)
        return buildResponseEntity(apiError)
    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param ex      HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    protected fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders?,
        status: HttpStatus?,
        request: WebRequest
    ): ResponseEntity<Any> {
        return buildResponseEntity(
            ApiError(
                HttpStatus.BAD_REQUEST,
                message = "Malformed JSON request",
                debugMessage = ex.localizedMessage
            )
        )
    }

    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param ex      HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    protected fun handleHttpMessageNotWritable(
        ex: HttpMessageNotWritableException,
        headers: HttpHeaders?,
        status: HttpStatus?,
        request: WebRequest?
    ): ResponseEntity<Any> {
        return buildResponseEntity(
            ApiError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                message = "Error writing JSON output",
                debugMessage = ex.localizedMessage
            )
        )
    }

    /**
     * Handle NoHandlerFoundException.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    protected fun handleNoHandlerFoundException(
        ex: NoHandlerFoundException,
        headers: HttpHeaders?,
        status: HttpStatus?,
        request: WebRequest?
    ): ResponseEntity<Any> {
        val apiError = ApiError(
            HttpStatus.BAD_REQUEST,
            message = String.format(
                "Could not find the %s method for URL %s",
                ex.httpMethod,
                ex.requestURL
            ),
            debugMessage = ex.message
        )
        return buildResponseEntity(apiError)
    }

    /**
     * Handle javax.persistence.EntityNotFoundException
     */
    @ExceptionHandler(EntityNotFoundException::class)
    protected fun handleEntityNotFound(ex: EntityNotFoundException): ResponseEntity<Any> {
        return buildResponseEntity(ApiError(HttpStatus.NOT_FOUND, ex))
    }

    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     *
     * @param ex the DataIntegrityViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(DataIntegrityViolationException::class)
    protected fun handleDataIntegrityViolation(
        ex: DataIntegrityViolationException,
        request: WebRequest?
    ): ResponseEntity<Any> {
        if (ex.cause is ConstraintViolationException) {
            return buildResponseEntity(
                ApiError(
                    HttpStatus.CONFLICT,
                    message = "Database error",
                    debugMessage = (ex.cause as ConstraintViolationException).localizedMessage
                )
            )
        }
        return buildResponseEntity(ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex))
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    protected fun handleMethodArgumentTypeMismatch(
        ex: MethodArgumentTypeMismatchException,
        request: WebRequest?
    ): ResponseEntity<Any> {
        val apiError = ApiError(
            HttpStatus.BAD_REQUEST,
            message = String.format(
                "The parameter '%s' of value '%s' could not be converted to type '%s'",
                ex.name,
                ex.value,
                ex.requiredType?.simpleName ?: "unknown"
            ), debugMessage = ex.message
        )
        return buildResponseEntity(apiError)
    }

//    @ExceptionHandler(InvalidAuthenticationTokenException::class)
//    fun handleInvalidAuthenticationToken(
//        ex: InvalidAuthenticationTokenException,
//        request: WebRequest?
//    ): ResponseEntity<Any> {
//        val apiError = ApiError(HttpStatus.UNAUTHORIZED)
//        apiError.setMessage(String.format("Expired or invalid authentication token"))
//        apiError.setDebugMessage(ex.getMessage())
//        return buildResponseEntity(apiError)
//    }

//    @ExceptionHandler(BadCredentialsException::class)
//    protected fun handleBadCredentials(ex: BadCredentialsException, request: WebRequest?): ResponseEntity<Any> {
//        val apiError = ApiError(HttpStatus.UNAUTHORIZED)
//        apiError.setMessage(String.format("Invalid username/password combination"))
//        apiError.setDebugMessage(ex.getMessage())
//        return buildResponseEntity(apiError)
//    }

    @ExceptionHandler(OptimisticEntityLockException::class)
    protected fun handleConcurrentEdit(ex: OptimisticEntityLockException, request: WebRequest?): ResponseEntity<Any> {
        val apiError = ApiError(HttpStatus.CONFLICT, "Concurrent Edit Detected", ex)
        return buildResponseEntity(apiError)
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception, request: WebRequest?): ResponseEntity<Any> {
        val apiError = ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex)
        return buildResponseEntity(apiError)
    }

    private fun buildResponseEntity(apiError: ApiError): ResponseEntity<Any> {
        return ResponseEntity<Any>(apiError, apiError.status)
    }
}