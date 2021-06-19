import dev.fritz2.binding.storeOf
import dev.fritz2.dom.html.render
import dev.fritz2.remote.http
import kotlinx.coroutines.flow.map
import model.Framework

fun main() {
    val frameworkStore = storeOf(Framework("fritz2"))

    render {
        p {
            +"This site uses: "
            b { frameworkStore.data.map { it.name }.asText() }
        }
    }
//    http("").authentication()
}