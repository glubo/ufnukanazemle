package model

import dev.fritz2.lenses.Lenses

@Lenses
data class MedicalCentre (
    var id: Long,
    var name: String,
    var address: String,
    var username: String,
)
