package cz.ufnukanazemle.be

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import model.MedicalCentreDTO


@MappedEntity()
data class MedicalCentre(
    @field:GeneratedValue
    @field:Id
    var id: Long?,
    var name: String,
    var address: String,
    var username: String,
) {
    fun toModel() = MedicalCentreDTO(
        id = this.id ?: 0L,
        name = this.name,
        address = this.address,
        username = this.username
    )
    companion object {
        fun fromDTO(medicalCentre: MedicalCentreDTO) =
            MedicalCentre(
                id = if (medicalCentre.id != 0L) medicalCentre.id else null,
                name = medicalCentre.name,
                address = medicalCentre.address,
                username = medicalCentre.username
            )
    }
}
