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
import io.micronaut.security.annotation.Secured
import model.MedicalCentreDTO
import reactor.core.publisher.Mono

@Secured(Roles.ROLE_ADMIN)
@Controller("/admin")
class AdminController(
    private val medicalCentreRepository: MedicalCentreRepository,
) {

    @Get("medicalCentres")
    fun getMedicalCentres(): Mono<Either<BaseError, Collection<MedicalCentreDTO>>> = medicalCentreRepository.findAll()
        .map { it.toModel() }
        .collectList()
        .map { it.right() }

    fun addMedicalCentre(medicalCentre: MedicalCentreDTO): Mono<Either<BaseError, MedicalCentreDTO>> =
        medicalCentreRepository.save(MedicalCentre.fromDTO(medicalCentre))
            .map { it.toModel().right() as Either<BaseError, MedicalCentreDTO> }
            .doOnError { BaseError(it.message ?: "unknown error saving medical centre ").left() }
//
//    @Secured(Roles.ROLE_USER)
//    @Get("/role/a")
//    fun indexxx(): List<MedicalCentre> {
//        return medicalCentreRepository.find()
//    }

}