import dev.fritz2.binding.RootStore
import dev.fritz2.dom.html.RenderContext
import kotlinx.browser.window
import model.L
import model.MedicalCentre
import model.MedicalCentreDTO
import model.MedicalStoreListData
import org.w3c.dom.url.URL
import org.w3c.dom.url.URLSearchParams
import kotlin.random.Random

val initMedicalStoreListData = MedicalStoreListData(
    listOf(
        MedicalCentreDTO(123, "Jmeno", "Adresa", "uzivatel")
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
            false
        )
    }
}

fun RenderContext.medicalCentreList() {
    section("col-12") {
        h2 {
            +"Medical Centres "
        }
        a {
            href(OAuthClient().authUri())
            +"login "
            +OAuthClient().authUri()
        }

        div {
            +"location search: ${window.location.search}"
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
