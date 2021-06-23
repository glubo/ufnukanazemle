package model

import dev.fritz2.lenses.Lenses


@Lenses
data class MedicalStoreListData (
    val list: List<MedicalCentre>,
    val loader: Boolean
)