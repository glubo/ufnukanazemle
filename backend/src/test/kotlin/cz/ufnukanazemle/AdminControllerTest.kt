package cz.ufnukanazemle

import cz.ufnukanazemle.configuration.R2dbcMigrate
import cz.ufnukanazemle.controller.AdminController
import io.micronaut.data.r2dbc.operations.R2dbcOperations
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.mono
import model.MedicalCentreDTO
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import reactor.kotlin.core.publisher.toMono
import javax.inject.Inject
import kotlin.RuntimeException


@MicronautTest(transactional = false)
class AdminControllerTest {
    val logger = LoggerFactory.getLogger(this::class.java)
    @Inject
    lateinit var adminController: AdminController

    @Inject
    lateinit var r2dbcMigrate: R2dbcMigrate

    @Inject
    lateinit var r2dbcOperations: R2dbcOperations

    @Test
    fun `getMedicalCentres can be called`() {
        val ret = adminController.getMedicalCentres().block()
        Assertions.assertTrue(ret.isRight(), "getMedicalCentres is right")
    }

    @Test
//    @Transactional
    fun `addMedicalCentre can be called`() {
        try {
            logger.info("TRY")
            val ret = r2dbcOperations.withTransaction {
                logger.info("withTransaction")

                val ret = mono(Dispatchers.IO) {
                    logger.info("coroutine")
                     adminController.upsertMedicalCentre(
                        MedicalCentreDTO(
                            id = 0,
                            name = "testovaci centruma",
                            address = "nejaka adresa",
                            username = "test1",
                        )
                    )
                }
                logger.info("error")
//                Mono.error<Either<BaseError, MedicalCentreDTO>>(RollbackOnlyException())
            ret
            }.toMono().block()
        } catch (e:RollbackOnlyException) {
            val a = "sdada"
        }
    }

    class RollbackOnlyException:RuntimeException()
}