package com.example.dietafacilpweb

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.roundToInt

data class Food( // Data class - Tipo de classe que armazena atirbutos
    val nome: String,
    val imagem: String?,
    val kcal: Double,
    val proteina: Double,
    val carbo: Double,
    val gordura: Double,
    val fibra: Double,
    val sodioMg: Double,
    val acucar: Double,
    val id: Long = 0,
    val unidade: String = "g",
    val tipoUnidade: String? = null,
    val porcao: Double,
) {
    val emoji: String get() = imagem?.takeIf { '.' !in it } ?: "🍽️" // Se falhar ao carregar a imagem do alimento, exibe o emoji de prato no lugar
}

// Essa é a classe que adiciona um alimento na refeição
data class Entrada(val food: Food, val gramas: Double, val id: Long = 0) {
    private val massa get() = gramas
    val kcal get() = food.kcal * massa
    val proteina get() = food.proteina * massa
    val carbo get() = food.carbo * massa
    val gordura get() = food.gordura * massa
    val fibra get() = food.fibra * massa
    val sodioMg get() = food.sodioMg * massa
    val acucar get() = food.acucar * massa
    val unidade get() = food.unidade // Adicionei esse atributo aqui para evitar que líquidos apareçam medidos com gramas na refeição
}

// Classe que representa a refeição do dia
class Refeicao(val id: Long, nome: String, entradas: List<Entrada> = emptyList()) {
    var nome by mutableStateOf(nome)
    val itens = mutableStateListOf<Entrada>().apply { addAll(entradas) }
    val kcal: Double get() = itens.sumOf { it.kcal }
}

fun Double.fmt(): String { // Função para formatação de valores
    val r = (this * 10).roundToInt() / 10.0
    return if (r % 1.0 == 0.0) r.toInt().toString() else r.toString()
}
