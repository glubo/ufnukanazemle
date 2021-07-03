package cz.ufnukanazemle

import cz.ufnukanazemle.configuration.R2dbcMigrate
import cz.ufnukanazemle.controller.AdminController
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import model.MedicalCentreDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import javax.inject.Inject
import javax.transaction.Transactional


@MicronautTest(transactional = true)
class AdminControllerTest {
    @Inject
    lateinit var adminController: AdminController
    @Inject
    lateinit var r2dbcMigrate: R2dbcMigrate

    @Test
    fun `getMedicalCentres can be called`() {
        val ret = adminController.getMedicalCentres().block()
        Assertions.assertTrue(ret.isRight(), "getMedicalCentres is right")
    }

    @Test
    @Transactional
    fun `addMedicalCentre can be called`() {
        val ret = adminController.addMedicalCentre(
            MedicalCentreDTO(
                id = 0,
                name = "testovaci centrum",
                address = "nejaka adresa",
                username = "test1",
            )
        ).block()
        Assertions.assertTrue(ret.isRight(), "addMedicalCentre is right")
    }
}