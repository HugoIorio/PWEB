package com.example.dietafacilpweb.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun criarDriver(): SqlDriver =
        NativeSqliteDriver(DietaDatabase.Schema, "dieta.db")
}
