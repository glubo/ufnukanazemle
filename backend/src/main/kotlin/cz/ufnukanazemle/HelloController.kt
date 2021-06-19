package cz.ufnukanazemle

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import cz.ufnukanazemle.be.MedicalCentre
import cz.ufnukanazemle.be.MedicalCentreRepository
import cz.ufnukanazemle.error.BaseError
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import model.Framework
import reactor.core.publisher.Mono

@Controller("/hello")
class HelloController(
    private val medicalCentreRepository: MedicalCentreRepository,
) {

    @SecurityRequirement(name = "oidc")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/")
    fun index(): Either<BaseError, List<String>> {
        Framework("asdas")
        return listOf("Hello World").right()
    }

    @SecurityRequirement(name = "oidc")
    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Get("/error")
    fun error(): Either<BaseError, List<String>> {
        return BaseError("trada").left()
    }

    @SecurityRequirement(name = "oidc")
    @Secured(Roles.ROLE_USER)
    @Get("/role/")
    fun indexx(): Either<BaseError, Collection<MedicalCentre>> {
        return medicalCentreRepository.findAll().toIterable().toList().right()
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get("/anon/")
    fun anon(): Mono<Either<BaseError, Collection<MedicalCentre>>> {
        return medicalCentreRepository.findAll()
            .collectList()
            .map { it.right() }
    }
//
//    @Secured(Roles.ROLE_USER)
//    @Get("/role/a")
//    fun indexxx(): List<MedicalCentre> {
//        return medicalCentreRepository.find()
//    }

}