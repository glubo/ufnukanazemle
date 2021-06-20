package cz.ufnukanazemle.configuration

import io.micronaut.context.annotation.Property
import io.micronaut.core.async.publisher.Publishers
import io.micronaut.http.HttpMethod
import io.micronaut.http.HttpRequest
import io.micronaut.http.MediaType
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.HttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import io.micronaut.http.server.netty.types.files.NettySystemFileCustomizableResponseType
import org.reactivestreams.Publisher


@Filter(methods = [HttpMethod.GET], patterns = ["/**/swagger-ui", "/**/swagger/*.yml"])
class OpenApiFilter(
    @Property(name = "base-domain")
    private val baseDomain: String,
    @Property(name = "oauth2.jwt-auth-uri")
    private val authUri: String,
    @Property(name = "oauth2.jwt-token-uri")
    private val tokenUri: String,
): HttpServerFilter {
    override fun doFilter(request: HttpRequest<*>?, chain: ServerFilterChain): Publisher<MutableHttpResponse<*>> {
        return Publishers.map(chain.proceed(request)) {
            val body = it.body.get()
            when(body) {
                is NettySystemFileCustomizableResponseType -> it.apply {
                    body(processPlaceholders(body.file.readText()))
                    contentType(MediaType.TEXT_HTML_TYPE)
                }
                else -> it
            }
        }
    }

    private fun processPlaceholders(text: String): String =
        text.replace("BASE_DOMAIN", baseDomain)
            .replace("OAUTH2_AUTH_URI", authUri)
            .replace("OAUTH2_TOKEN_URI", tokenUri)

}