package cz.ufnukanazemle.configuration

import arrow.core.Either
import cz.ufnukanazemle.error.BaseError
import io.micronaut.core.async.publisher.Publishers
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponseFactory
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.Filter
import io.micronaut.http.filter.OncePerRequestHttpServerFilter
import io.micronaut.http.filter.ServerFilterChain
import io.micronaut.http.filter.ServerFilterPhase
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory


@Filter("/**")
class ErrorFilter(
) : OncePerRequestHttpServerFilter() {
    override fun getOrder(): Int {
        return ServerFilterPhase.RENDERING.after()
    }

    override fun doFilterOnce(request: HttpRequest<*>, chain: ServerFilterChain): Publisher<MutableHttpResponse<*>> =
        Publishers.map(chain.proceed(request)) { response ->
            val body = response.body.orElse(null)
            when(body){
                is Either<*, *> -> (body as Either<BaseError, *>).fold(
                    {mapError(response, it)},
                    {response.body(it)}
                )
                else -> response
            }
        }

    private fun mapError(response: MutableHttpResponse<*>, it: BaseError) =
        response.status(it.status, it.message)
            .body(ErrorResponse(it.message))

    data class ErrorResponse(
        val message: String
    )
}