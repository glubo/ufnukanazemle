package cz.ufnukanazemle.be

import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.r2dbc.repository.ReactorCrudRepository
import reactor.core.publisher.Flux


@R2dbcRepository(dialect = Dialect.MYSQL)
interface MedicalCentreRepository: ReactorCrudRepository<MedicalCentre, Long> {
    abstract override fun findAll() : Flux<MedicalCentre>
}