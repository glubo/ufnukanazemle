package cz.ufnukanazemle.configuration

import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.ConfigurationProperties
import io.micronaut.context.annotation.Factory
import io.r2dbc.spi.ConnectionFactory
import name.nkonev.r2dbc.migrate.core.R2dbcMigrate
import name.nkonev.r2dbc.migrate.core.R2dbcMigrateProperties
import name.nkonev.r2dbc.migrate.core.SqlQueries
import org.slf4j.LoggerFactory
import javax.inject.Singleton


@Singleton
class R2dbcMigrate(
    connectionFactory: ConnectionFactory?,
    properties: MicronautR2dbcMigrateProperties?,
    maybeUserDialect: SqlQueries?,
    reader: MicronautResourceReader,
) {
    init {
        val invoker = R2dbcMigrateBlockingInvoker(connectionFactory, properties, maybeUserDialect, reader)
        invoker.migrate()
    }

    class R2dbcMigrateBlockingInvoker(
        private val connectionFactory: ConnectionFactory?,
        private val properties: R2dbcMigrateProperties?,
        maybeUserDialect: SqlQueries?,
        private val reader: MicronautResourceReader,
    ) {
        private val logger = LoggerFactory.getLogger(this::class.java)
        private val maybeUserDialect: SqlQueries? = maybeUserDialect
        fun migrate() {
            logger.info("Starting R2DBC migration")
            R2dbcMigrate.migrate(connectionFactory, properties, reader,
                maybeUserDialect).block()
            logger.info("End of R2DBC migration")
        }

    }

    @ConfigurationProperties("r2dbc.migrate")
    class MicronautR2dbcMigrateProperties : R2dbcMigrateProperties()
}