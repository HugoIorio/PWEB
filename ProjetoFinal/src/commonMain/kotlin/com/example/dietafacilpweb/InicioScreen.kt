package com.example.dietafacilpweb

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt
// Essa é a tela inicial do aplicativo
// Meta diária de calorias - Está com o valor fixo de 2000 pois ainda não fizemos as telas para definição da meta
private const val META_KCAL = 2000

@Composable
fun InicioScreen( // Construção geral da tela
    refeicoes: List<Refeicao>,
    onRegistrarConsumo: (Refeicao) -> Unit,
    onAdicionarRefeicao: (String) -> Unit,
    onExcluirRefeicao: (Refeicao) -> Unit,
) {
    var adicionando by remember { mutableStateOf(false) }
    var editandoNome by remember { mutableStateOf<Refeicao?>(null) }
    var excluindo by remember { mutableStateOf<Refeicao?>(null) }

    Column(Modifier.fillMaxSize()) {
        AppBar(titulo = "Início")
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp),
        ) {
            ResumoDiario(consumido = refeicoes.sumOf { it.kcal })
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                Text("Refeições", fontSize = 24.sp, fontWeight = FontWeight.Medium, color = Cores.OnSurface)
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    refeicoes.forEach { r ->
                        CartaoRefeicao(
                            r,
                            onEditarNome = { editandoNome = r },
                            onExcluir = { excluindo = r },
                            onRegistrar = { onRegistrarConsumo(r) },
                        )
                    }
                    BotaoAdicionarRefeicao { adicionando = true }
                }
            }
            MacrosCard(refeicoes)
        }
    }

    if (adicionando) {
        DialogNomeRefeicao(
            titulo = "Nova refeição",
            inicial = "",
            onCancelar = { adicionando = false },
            onConfirmar = { onAdicionarRefeicao(it); adicionando = false },
        )
    }
    editandoNome?.let { r ->
        DialogNomeRefeicao(
            titulo = "Editar nome da refeição",
            inicial = r.nome,
            onCancelar = { editandoNome = null },
            onConfirmar = { r.nome = it; editandoNome = null },
        )
    }
    excluindo?.let { r ->
        AlertDialog(
            onDismissRequest = { excluindo = null },
            title = { Text("Excluir refeição") },
            text = { Text("Deseja realmente excluir \"${r.nome}\"?") },
            confirmButton = {
                TextButton(onClick = { onExcluirRefeicao(r); excluindo = null }) {
                    Text("Excluir", color = Cores.Error)
                }
            },
            dismissButton = { TextButton(onClick = { excluindo = null }) { Text("Cancelar") } },
        )
    }
}

@Composable
private fun BotaoAdicionarRefeicao(onClick: () -> Unit) {
    Box(
        Modifier.fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .border(1.dp, Cores.Primary, RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text("Adicionar Refeição", color = Cores.Primary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun DialogNomeRefeicao(
    titulo: String,
    inicial: String,
    onCancelar: () -> Unit,
    onConfirmar: (String) -> Unit,
) {
    var texto by remember { mutableStateOf(inicial) }
    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text(titulo) },
        text = {
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it },
                label = { Text("Nome da refeição") },
                placeholder = { Text("Ex: Lanche Pré-Treino") },
                singleLine = true,
            )
        },
        confirmButton = {
            TextButton(onClick = { texto.trim().takeIf { it.isNotEmpty() }?.let(onConfirmar) }) { Text("Salvar") }
        },
        dismissButton = { TextButton(onClick = onCancelar) { Text("Cancelar") } },
    )
}

@Composable
private fun ResumoDiario(consumido: Double) { // Quadro verde superior, informa total de calorias das refeições
    val fracao = (consumido / META_KCAL).toFloat().coerceIn(0f, 1f)
    val restante = META_KCAL - consumido
    Surface(color = Cores.PrimaryContainer, shape = RoundedCornerShape(12.dp), shadowElevation = 6.dp) {
        Column(Modifier.fillMaxWidth().padding(24.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                Column(Modifier.weight(1f)) {
                    Text("Resumo Diário", color = Cores.OnPrimaryContainer.copy(alpha = 0.9f), fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(consumido.roundToInt().milhar(), color = Cores.OnPrimaryContainer, fontSize = 36.sp, fontWeight = FontWeight.Bold)
                        Text(" / ${META_KCAL.milhar()} kcal", color = Cores.OnPrimaryContainer, fontSize = 18.sp, modifier = Modifier.padding(bottom = 6.dp))
                    }
                }
            }
            Box(Modifier.fillMaxWidth().height(16.dp).clip(RoundedCornerShape(50)).background(Color.Black.copy(alpha = 0.1f))) {
                Box(Modifier.fillMaxWidth(fracao).fillMaxHeight().clip(RoundedCornerShape(50)).background(Color.White))
            }
            Text(
                when {
                    restante > 0.5 -> "Faltam ${restante.roundToInt().milhar()} kcal para atingir sua meta."
                    restante < -0.5 -> "Você ultrapassou a meta em ${(-restante).roundToInt().milhar()} kcal."
                    else -> "Meta diária atingida!"
                },
                color = Cores.OnPrimaryContainer.copy(alpha = 0.8f),
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
private fun CartaoRefeicao(
    r: Refeicao,
    onEditarNome: () -> Unit,
    onExcluir: () -> Unit,
    onRegistrar: () -> Unit,
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Cores.SurfaceHighest),
        shadowElevation = 2.dp,
    ) {
        Column(Modifier.fillMaxWidth().padding(12.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                    Text(
                        r.nome,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Cores.OnSurface,
                    )
                    Text("${r.kcal.roundToInt()} kcal", fontSize = 12.sp, color = Cores.OnSurfaceVariant)
                }
                BotaoIcone("✏️", Cores.Outline, onEditarNome)
                BotaoIcone("🗑️", Cores.Error, onExcluir)
            }
            r.itens.forEach { e ->
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = Cores.SurfaceLow)
                Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("${e.food.nome} (${e.gramas.fmt()}${e.food.unidade})", color = Cores.OnSurfaceVariant, fontSize = 16.sp, modifier = Modifier.weight(1f))
                    Text("${e.kcal.roundToInt()} kcal", color = Cores.OnSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
            }
            Spacer(Modifier.height(8.dp))
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                BotaoPilula("＋ Registrar consumo", onClick = onRegistrar)
            }
        }
    }
}

@Composable
private fun MacrosCard(refeicoes: List<Refeicao>) {
    val itens = refeicoes.flatMap { it.itens }
    val proteina = itens.sumOf { it.proteina }
    val carbo = itens.sumOf { it.carbo }
    val gordura = itens.sumOf { it.gordura }
    val fibra = itens.sumOf { it.fibra }
    val sodio = itens.sumOf { it.sodioMg }
    val acucar = itens.sumOf { it.acucar }

    Surface(
        color = Cores.SurfaceLow,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Cores.SurfaceHigh),
    ) {
        Column(Modifier.fillMaxWidth().padding(24.dp), verticalArrangement = Arrangement.spacedBy(24.dp)) {
            Text("MACROS E NUTRIENTES", color = Cores.Primary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.sp)
            HorizontalDivider(color = Cores.SurfaceHighest)
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                LinhaMacro("Proteínas", "${proteina.fmt()} g")
                LinhaMacro("Carboidratos", "${carbo.fmt()} g")
                LinhaMacro("Gorduras", "${gordura.fmt()} g")
                LinhaMacro("Fibras", "${fibra.fmt()} g")
                LinhaMacro("Sódio", "${sodio.roundToInt()} mg")
                LinhaMacro("Açúcar", "${acucar.fmt()} g")
            }
        }
    }
}

@Composable
private fun LinhaMacro(rotulo: String, valor: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(rotulo, color = Cores.OnSurfaceVariant)
        Text(valor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

/** 1200 -> "1.200" (separador de milhar no padrão brasileiro). */
private fun Int.milhar(): String = toString().reversed().chunked(3).joinToString(".").reversed()