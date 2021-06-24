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
import io.micronaut.http.server.netty.types.files.NettyStreamedFileCustomizableResponseType
import io.micronaut.http.server.netty.types.files.NettySystemFileCustomizableResponseType
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory


@Filter(methods = [HttpMethod.GET], patterns = ["/**/swagger-ui", "/**/swagger/*.yml"])
class OpenApiFilter(
    @Property(name = "base-domain")
    private val baseDomain: String,
    @Property(name = "oauth2.jwt-auth-uri")
    private val authUri: String,
    @Property(name = "oauth2.jwt-token-uri")
    private val tokenUri: String,
): HttpServerFilter {
    private val logger = LoggerFactory.getLogger(this::class.java)
    override fun doFilter(request: HttpRequest<*>, chain: ServerFilterChain): Publisher<MutableHttpResponse<*>> {
        return Publishers.map(chain.proceed(request)) {
            val body = it.body.get()
            when(body) {
                is NettySystemFileCustomizableResponseType -> it.apply {
                    body(processPlaceholders(body.file.readText()))
                    contentType(toContentType(request))
                }
                is NettyStreamedFileCustomizableResponseType -> it.apply {
                    val text = body.inputStream.readAllBytes()
                    body(processPlaceholders(text.toString(it.characterEncoding)))
                    contentType(toContentType(request))
                }
                else -> {
                    logger.warn("Unexpected response type: ${it}")
                    it
                }
            }
        }
    }

    private fun toContentType(request: HttpRequest<*>) = when(request.path.endsWith("yml")) {
        true -> MediaType.APPLICATION_YAML_TYPE
        else -> MediaType.TEXT_HTML_TYPE
    }
    private fun processPlaceholders(text: String): String =
        text.replace("BASE_DOMAIN", baseDomain)
            .replace("OAUTH2_AUTH_URI", authUri)
            .replace("OAUTH2_TOKEN_URI", tokenUri)

}