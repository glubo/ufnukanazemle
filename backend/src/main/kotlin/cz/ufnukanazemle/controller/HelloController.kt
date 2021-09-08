package cz.ufnukanazemle.controller

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import cz.ufnukanazemle.be.MedicalCentreRepository
import cz.ufnukanazemle.error.BaseError
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import model.OAuth

@Controller("/hello")
class HelloController(
    private val medicalCentreRepository: MedicalCentreRepository,
) {

    @SecurityRequirement(name = "oidc")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/")
    fun index(authentication: Authentication): Either<BaseError, List<String>> {
        OAuth("asdas")
        return listOf("Hello World").right()
    }

    @SecurityRequirement(name = "oidc")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/error")
    fun error(): Either<BaseError, List<String>> {
        return BaseError("trada").left()
    }
//
//    @Secured(Roles.ROLE_USER)
//    @Get("/role/a")
//    fun indexxx(): List<MedicalCentre> {
//        return medicalCentreRepository.find()
//    }

}
