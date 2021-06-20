package cz.ufnukanazemle.configuration

import io.micronaut.cli.io.support.PathMatchingResourcePatternResolver
import io.micronaut.cli.io.support.Resource
import name.nkonev.r2dbc.migrate.reader.MigrateResource
import name.nkonev.r2dbc.migrate.reader.MigrateResourceReader
import org.slf4j.LoggerFactory
import java.io.InputStream
import javax.inject.Singleton


@Singleton
class MicronautResourceReader : MigrateResourceReader {
    private val logger = LoggerFactory.getLogger(this::class.java)
    override fun getResources(resourcesPath: String?): List<MicronautMigrateResource> {
        val resolver = PathMatchingResourcePatternResolver()
        val resources = resolver.getResources(resourcesPath)
            .map {
                logger.info("Found migration ${it.file}")
                MicronautMigrateResource(it)
            }
        return resources
    }

    class MicronautMigrateResource(private val resource: Resource) : MigrateResource {
        override fun isReadable(): Boolean = resource.isReadable

        override fun getInputStream(): InputStream = resource.inputStream

        override fun getFilename(): String = resource.filename
    }
}