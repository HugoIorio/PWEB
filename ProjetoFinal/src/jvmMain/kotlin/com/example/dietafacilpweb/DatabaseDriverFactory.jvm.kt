package com.example.dietafacilpweb.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import java.io.File
import java.util.Properties

/** Versão para Desktop (útil para testar no computador). O arquivo fica na pasta do usuário. */
actual class DatabaseDriverFactory {
    actual fun criarDriver(): SqlDriver {
        val pasta = File(System.getProperty("user.home"), ".dietafacil").apply { mkdirs() }
        val arquivo = File(pasta, "dieta.db")
        val novo = !arquivo.exists()
        val driver = JdbcSqliteDriver(
            url = "jdbc:sqlite:${arquivo.absolutePath}",
            properties = Properties().apply { put("foreign_keys", "true") },
        )
        if (novo) DietaDatabase.Schema.create(driver)
        return driver
    }
}
