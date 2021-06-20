package cz.ufnukanazemle

import io.micronaut.context.annotation.Bean
import io.micronaut.runtime.Micronaut.*
import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType
import io.swagger.v3.oas.annotations.info.*
import io.swagger.v3.oas.annotations.security.OAuthFlow
import io.swagger.v3.oas.annotations.security.OAuthFlows
import io.swagger.v3.oas.annotations.security.OAuthScope
import io.swagger.v3.oas.annotations.security.SecurityScheme

@SecurityScheme(name = "oidc",
    type = SecuritySchemeType.OAUTH2,
    scheme = "bearer",
    bearerFormat = "jwt",
    flows = OAuthFlows(
        authorizationCode = OAuthFlow(
            authorizationUrl = "https://keycloak.glubo.cz/auth/realms/ufnukanazemle/protocol/openid-connect/auth",
            tokenUrl = "https://keycloak.glubo.cz/auth/realms/ufnukanazemle/protocol/openid-connect/token",
//            refreshUrl = "",
            scopes = [
                OAuthScope(name = "openid"),
                OAuthScope(name = "profilea"),
                OAuthScope(name = "email"),
                OAuthScope(name = "address"),
                OAuthScope(name = "phone"),
                OAuthScope(name = "offline_access"),
            ]))
)
@OpenAPIDefinition(
    info = Info(
            title = "backend",
            version = "0.0"
    )
)
object Api {
}
fun main(args: Array<String>) {
	build()
	    .args(*args)
		.packages("cz.ufnukanazemle")
        .eagerInitSingletons(true)
//        .eagerInitAnnotated(Bean::class.java)
		.start()
}

