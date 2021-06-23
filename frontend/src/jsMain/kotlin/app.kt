import dev.fritz2.binding.RootStore
import dev.fritz2.binding.storeOf
import dev.fritz2.dom.html.render
import kotlinx.coroutines.flow.map
import model.Framework
import model.MedicalCentre

fun main() {
    val frameworkStore = storeOf(Framework("fritz2"))
    val seq = object : RootStore<List<String>>(listOf("one", "two", "three")) {
        var count = 0

        val addItem = handle { list ->
            count++
            list + "yet another item no. $count"
        }
        val deleteItem = handle<String> { list, current ->
            list.minus(current)
        }
    }

    render {
        p {
            +"This site uses: "
            b { frameworkStore.data.map { it.name }.asText() }
        }
        section {
            div("row") {
                ul ("list-group") {
                    seq.data.renderEach { s ->
                        li ("list-group-item") {
                            span ("col-md-auto") { +s }
                            button("btn btn-danger float-end", id = "delete-btn") {
                                +"delete"
                                clicks.map { console.log("deleting $s"); s } handledBy seq.deleteItem
                            }
                        }
                    }
                }
                button("btn btn-primary") {
                    +"add an item"
                    clicks handledBy seq.addItem
                }
            }
        }
        medicalCentreList()
    }

//    http("").authentication()
}