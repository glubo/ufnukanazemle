package cz.ufnukanazemle.error

import io.micronaut.http.HttpStatus


open class BaseError(
    val message: String = "Unknown error",
    val status: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR,
)