package com.example.dietafacilpweb

import androidx.compose.runtime.mutableStateListOf
import com.example.dietafacilpweb.db.DatabaseDriverFactory
import com.example.dietafacilpweb.db.DietaRepository
import com.example.dietafacilpweb.db.criarBanco

/**
 * Estado do cardápio usado pelas telas. Cada ação grava no banco e depois atualiza a lista
 * em memória, que a interface observa.
 */
class CardapioState(private val repo: DietaRepository) {
    val refeicoes = mutableStateListOf<Refeicao>()

    init {
        repo.prepararBanco()
        refeicoes.addAll(repo.carregarRefeicoes())
    }

    fun sugestoes(): List<Food> = repo.sugestoes()

    fun buscarAlimentos(termo: String): List<Food> = repo.buscarAlimentos(termo)

    fun adicionarRefeicao(nome: String) {
        val id = repo.inserirRefeicao(nome)
        refeicoes.add(Refeicao(id, nome))
    }

    fun renomearRefeicao(r: Refeicao, nome: String) {
        repo.renomearRefeicao(r.id, nome)
        r.nome = nome
    }

    fun excluirRefeicao(r: Refeicao) {
        repo.excluirRefeicao(r.id)
        refeicoes.remove(r)
    }

    fun adicionarAlimento(r: Refeicao, food: Food, gramas: Double = 100.0) {
        val id = repo.inserirItem(r.id, food.id, gramas)
        r.itens.add(Entrada(food, gramas, id))
    }

    fun alterarQuantidade(r: Refeicao, e: Entrada, gramas: Double) {
        repo.atualizarQuantidade(e.id, gramas)
        val i = r.itens.indexOf(e)
        if (i >= 0) r.itens[i] = e.copy(gramas = gramas)
    }

    fun removerItem(r: Refeicao, e: Entrada) {
        repo.excluirItem(e.id)
        r.itens.remove(e)
    }
}

/** Ponto único para criar o estado a partir do driver de cada plataforma. */
fun criarCardapioState(factory: DatabaseDriverFactory): CardapioState =
    CardapioState(DietaRepository(criarBanco(factory)))
