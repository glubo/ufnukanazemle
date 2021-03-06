package cz.ufnukanazemle.controller

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import cz.ufnukanazemle.Roles
import cz.ufnukanazemle.be.MedicalCentre
import cz.ufnukanazemle.be.MedicalCentreRepository
import cz.ufnukanazemle.error.BaseError
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.security.annotation.Secured
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import kotlinx.coroutines.reactive.awaitFirst
import model.MedicalCentreDTO
import reactor.core.publisher.Mono

@Secured(Roles.ROLE_ADMIN)
@SecurityRequirement(name = "oidc")
@Controller("/admin")
class AdminController(
    private val medicalCentreRepository: MedicalCentreRepository,
) {

    @Get("medical-centres")
    fun getMedicalCentres(): Mono<Either<BaseError, Collection<MedicalCentreDTO>>> = medicalCentreRepository.findAll()
        .map { it.toModel() }
        .collectList()
        .map { it.right() }

    @Post("medical-centres")
    @Schema(implementation =  MedicalCentreDTO::class )
    suspend fun upsertMedicalCentre(medicalCentre: MedicalCentreDTO): Either<BaseError, MedicalCentreDTO> =
        medicalCentreRepository.save(MedicalCentre.fromDTO(medicalCentre))
            .map { it.toModel().right() as Either<BaseError, MedicalCentreDTO> }
            .doOnError { BaseError(it.message ?: "unknown error saving medical centre ").left() }
            .awaitFirst()

}