package com.example.dietafacilpweb.db

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun criarDriver(): SqlDriver =
        AndroidSqliteDriver(DietaDatabase.Schema, context, "dieta.db")
}
