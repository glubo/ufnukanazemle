import dev.fritz2.binding.RootStore
import dev.fritz2.dom.html.RenderContext
import model.L
import model.MedicalCentre
import model.MedicalStoreListData

val initMedicalStoreListData = MedicalStoreListData(
    listOf(
        MedicalCentre(123, "Jmeno", "Adresa", "uzivatel")
    ),
    false,
)

object MedicalCentreListStore :
    RootStore<MedicalStoreListData>(initMedicalStoreListData) {
    val deleteItem = handle<Long> { data, id ->
        L.MedicalStoreListData.loader.set(
            L.MedicalStoreListData.list.set(
                data,
                data.list.filter { it.id != id }
            ),
            true
        )
    }
}

fun RenderContext.medicalCentreList() {
    section("col-12") {
        h2 {
            +"Medical Centres"
        }

        MedicalCentreListStore.data.render { medicalCentres ->
            if (medicalCentres.loader) {
                div("spinner-border") {
                    attr("role", "status")
                }
            }
            medicalCentres.list.forEach { medicalCentre ->
                div("card") {
                    div("card-body") {
                        p("card-title") {
                            +medicalCentre.name
                        }
                        button("btn btn-danger") {
                            +"Delete"
                            clicks.map {
                                console.log("deleting $medicalCentre")
                                medicalCentre.id
                            } handledBy MedicalCentreListStore.deleteItem
                        }
                    }

                }
            }
        }

    }
}
