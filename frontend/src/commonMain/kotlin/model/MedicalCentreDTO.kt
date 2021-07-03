
package model

import dev.fritz2.lenses.Lenses
import kotlinx.serialization.Serializable


@Lenses
@Serializable
data class MedicalCentreDTO(
    val id: Long,
    val name: String,
    val address: String,
    val username: String,
)
