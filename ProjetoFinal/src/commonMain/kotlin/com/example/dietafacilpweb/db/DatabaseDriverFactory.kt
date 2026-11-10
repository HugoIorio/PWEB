package com.example.dietafacilpweb.db

import app.cash.sqldelight.db.SqlDriver

expect class DatabaseDriverFactory { // Cria o driver SQL adequado para cada plataforma.
    fun criarDriver(): SqlDriver
}

fun criarBanco(factory: DatabaseDriverFactory): DietaDatabase = // Cria o banco de dados adequado para cada plataforma.
    DietaDatabase(factory.criarDriver())
