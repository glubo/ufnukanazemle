package cz.ufnukanazemle.repository

import io.micronaut.runtime.Micronaut.*

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("cz.ufnukanazemle.repository")
        .start()
}

