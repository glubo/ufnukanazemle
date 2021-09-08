import dev.fritz2.binding.storeOf
import dev.fritz2.dom.html.render
import dev.fritz2.remote.http
import kotlinx.browser.window
import model.OAuth
import org.w3c.dom.url.URL
import org.w3c.dom.url.URLSearchParams
import org.w3c.xhr.FormData

class OAuthClient(
    val authorizeUri: String = "https://keycloak.glubo.cz/auth/realms/pid/protocol/openid-connect/auth",
    tokenUri: String = "https://keycloak.glubo.cz/auth/realms/pid/protocol/openid-connect/token",
) {
    val tokenEndpoint = http(tokenUri)

    fun findStateInUri() = URLSearchParams(window.location.href).get("session_state")
    fun findCodeInUri() = URLSearchParams(window.location.href).get("code")

    fun getToken()  {
        val response = findCodeInUri()?.let {
            console.log("Found state $it")
                tokenEndpoint.acceptJson()
                    .formData(
                        FormData().apply {
                            append("grant_type", "authorization_code")
                            append("code", it)
                            append("client_id", "uz")
//                        redirect_uri=https://backend.ufnukanazemle.cz/swagger-ui/oauth2-redirect.html
                        }
                    )
        }
        console.log(response)
    }

    fun authUri() = URL(authorizeUri)
        .apply {
            searchParams.set("response_type", "code")
            searchParams.set("client_id", "uz")
            searchParams.set("scope", "profile")
            searchParams.set("state", "sdjasndlaskjdn")
//            searchParams.set("redirect_uri", "sdjasndlaskjdn")
        }.toString()
}

fun main() {
    val OAuthStore = storeOf(OAuth(OAuthClient().findStateInUri() ?: "none"))

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
            div {
                OAuthStore.data.asText()
            }
            medicalCentreList()

        }

    }

//    http("").authentication()
}
