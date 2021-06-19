package cz.ufnukanazemle.be

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity


@MappedEntity
data class MedicalCentre(
    @field:GeneratedValue
    @field:Id
    var id: Long?,
    var name: String,
    var address: String,
    var username: String,
)
