import dev.fritz2.binding.RootStore
import dev.fritz2.binding.storeOf
import dev.fritz2.dom.html.render
import kotlinx.coroutines.flow.map
import model.Framework

fun main() {
    val frameworkStore = storeOf(Framework("fritz2"))

    render {
        div("container-fluid") {
            nav("navbar navbar-dark bg-dark") {
                div("container-fluid") {
                    a("navbar-brand") {
                        href("/")
                        +"Ufnukana zemle"
                    }
                }
            }
            medicalCentreList()
        }

    }

//    http("").authentication()
}