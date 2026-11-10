package com.example.dietafacilpweb.db

import com.example.dietafacilpweb.Entrada
import com.example.dietafacilpweb.Food
import com.example.dietafacilpweb.Refeicao
import kotlin.math.round


class DietaRepository(private val db: DietaDatabase) { // Classe que faz a comunicação com o banco SQL
    private val alimentoQ get() = db.alimentoQueries
    private val refeicaoQ get() = db.refeicaoQueries
    private val itemQ get() = db.refeicaoAlimentoQueries

    fun prepararBanco() { //
        if (alimentoQ.contarAlimentos().executeAsOne() > 0L) return
        db.transaction {
            CatalogoInicial.alimentos.forEach { a ->
                alimentoQ.inserirAlimento(
                    a.nome, a.imagem,
                    a.calorias, a.proteinaG, a.carboidratoG, a.gorduraG,
                    a.fibraG, a.sodioMg, a.acucarG,
                    a.unidadeMedida, a.tipoUnidadePadrao, a.porcaoPadrao,
                )
            }
            CatalogoInicial.refeicoes.forEach { refeicaoQ.inserirRefeicao(it) }
        }
    }

    // ---- Alimentos ----

    fun sugestoes(): List<Food> = alimentoQ.selecionarSugestoes(::paraFood).executeAsList()

    fun buscarAlimentos(termo: String): List<Food> =
        alimentoQ.buscarPorNome(termo, ::paraFood).executeAsList()

    // ---- Refeições ----

    fun carregarRefeicoes(): List<Refeicao> =
        refeicaoQ.selecionarRefeicoes { id, nome -> id to nome }
            .executeAsList()
            .map { (id, nome) -> Refeicao(id, nome, carregarItens(id)) }

    private fun carregarItens(idRefeicao: Long): List<Entrada> =
        itemQ.selecionarItensDaRefeicao(idRefeicao) { idAssoc, _, qtd, idAli, nome, imagem, cal, prot, carb, gord, fib, sod, acu, un, tipo, porcao ->
            Entrada(paraFood(idAli, nome, imagem, cal, prot, carb, gord, fib, sod, acu, un, tipo, porcao), qtd, idAssoc)
        }.executeAsList()

    fun inserirRefeicao(nome: String): Long = db.transactionWithResult {
        refeicaoQ.inserirRefeicao(nome)
        refeicaoQ.ultimoIdInserido().executeAsOne()
    }

    fun renomearRefeicao(id: Long, nome: String) = refeicaoQ.atualizarNomeRefeicao(nome, id)

    fun excluirRefeicao(id: Long) = db.transaction {
        itemQ.excluirItensDaRefeicao(id)
        refeicaoQ.excluirRefeicao(id)
    }

    // ---- Itens da refeição ----

    fun inserirItem(idRefeicao: Long, idAlimento: Long, quantidade: Double): Long = db.transactionWithResult {
        itemQ.inserirItem(idRefeicao, idAlimento, quantidade)
        refeicaoQ.ultimoIdInserido().executeAsOne()
    }

    fun atualizarQuantidade(idAssociacao: Long, quantidade: Double) =
        itemQ.atualizarQuantidadeItem(quantidade, idAssociacao)

    fun excluirItem(idAssociacao: Long) = itemQ.excluirItem(idAssociacao)
}

private fun paraFood(
    id: Long,
    nome: String,
    imagem: String?, //String? pode armazenar valores null - Útil, pois uma imagem pode não carregar
    calorias: Double,
    proteina: Double,
    carbo: Double,
    gordura: Double,
    fibra: Double,
    sodio: Double,
    acucar: Double,
    unidade: String,
    tipo: String,
    porcao: Double,
) = Food(
    nome = nome,
    imagem = imagem,
    kcal = calorias,
    proteina = proteina,
    carbo = carbo,
    gordura = gordura,
    fibra = fibra,
    sodioMg = sodio,
    acucar = acucar,
    id = id,
    unidade = unidade,
    tipoUnidade = tipo,
    porcao = porcao
)
