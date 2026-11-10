package com.example.dietafacilpweb

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.background
// Essa é a tela de detalhes de uma refeição
@Composable
fun RefeicaoScreen(
    itens: List<Entrada>,
    onAdicionarAlimento: () -> Unit,
    onAlterarQuantidade: (Entrada, Double) -> Unit,
    onExcluirItem: (Entrada) -> Unit,
    onConfirmar: () -> Unit,
    onVoltar: () -> Unit,
) {
    var editando by remember { mutableStateOf<Int?>(null) }

    Column(Modifier.fillMaxSize()) {
        AppBar(titulo = "Configurar Refeição", onVoltar = onVoltar)
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp),
        ) {
            // Alimentos
            Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
                Row(Modifier.fillMaxWidth().padding(horizontal = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("Alimentos", fontSize = 24.sp, fontWeight = FontWeight.Medium, color = Cores.OnSurface, modifier = Modifier.weight(1f))
                    Text("${itens.size} ${if (itens.size == 1) "item" else "itens"}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Cores.Primary)
                }
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    itens.forEachIndexed { i, e ->
                        LinhaAlimento(e, onEditar = { editando = i }, onExcluir = { onExcluirItem(e) })
                    }
                }
                Row(
                    Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .border(2.dp, Cores.Primary.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .clickable(onClick = onAdicionarAlimento)
                        .padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("＋ Adicionar Alimento", color = Cores.Primary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            ResumoNutricional(itens)
        }

        // Botão confirmar
        Box(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp)) {
            Button(
                onClick = onConfirmar,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Cores.Primary),
            ) {
                Text("Confirmar Refeição  ✓", fontSize = 22.sp, fontWeight = FontWeight.Medium)
            }
        }
    }

    editando?.let { idx ->
        if (idx in itens.indices) {
            DialogGramas(
                atual = itens[idx],
                onCancelar = { editando = null },
                onSalvar = { g -> onAlterarQuantidade(itens[idx], g); editando = null },
            )
        } else editando = null
    }
}

@Composable
private fun LinhaAlimento(e: Entrada, onEditar: () -> Unit, onExcluir: () -> Unit) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Cores.SurfaceVariant),
        shadowElevation = 1.dp,
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            ImagemAlimento(e.food, Cores.PrimaryFixed.copy(alpha = 0.2f))
            Column(Modifier.weight(1f).padding(horizontal = 16.dp)) {
                Text(e.food.nome, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = Cores.OnSurface)
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("${e.gramas.fmt()}${e.unidade}", fontSize = 12.sp, color = Cores.OnSurfaceVariant)
                    Box(Modifier.clip(RoundedCornerShape(50)).background(Cores.PrimaryFixed.copy(alpha = 0.2f)).padding(horizontal = 8.dp, vertical = 2.dp)) {
                        Text("${e.kcal.toInt()} KCAL", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Cores.Primary, letterSpacing = 0.5.sp)
                    }
                }
            }
            BotaoIcone("✏️", Cores.Outline, onEditar)
            BotaoIcone("🗑️", Cores.Error, onExcluir)
        }
    }
}

@Composable
private fun ResumoNutricional(itens: List<Entrada>) {
    var aberto by remember { mutableStateOf(false) }
    val kcal = itens.sumOf { it.kcal }
    val prot = itens.sumOf { it.proteina }
    val carb = itens.sumOf { it.carbo }
    val gord = itens.sumOf { it.gordura }
    val fib = itens.sumOf { it.fibra }
    val sod = itens.sumOf { it.sodioMg }
    val acu = itens.sumOf { it.acucar }

    Surface(
        color = Cores.SurfaceLow,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Cores.SurfaceVariant),
    ) {
        Column(Modifier.fillMaxWidth().padding(24.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Resumo Nutricional", fontSize = 24.sp, fontWeight = FontWeight.Medium, color = Cores.OnSurface, modifier = Modifier.weight(1f))
                Text("⚡ ${kcal.toInt()} kcal", fontSize = 22.sp, fontWeight = FontWeight.Medium, color = Cores.Primary)
            }
            HorizontalDivider(color = Cores.OutlineVariant.copy(alpha = 0.3f))
            Row(Modifier.fillMaxWidth()) {
                Macro("PROTEÍNAS", "${prot.fmt()}g", destaque = true, modifier = Modifier.weight(1f))
                Macro("CARBOS", "${carb.fmt()}g", destaque = true, modifier = Modifier.weight(1f))
                Macro("GORDURAS", "${gord.fmt()}g", destaque = true, modifier = Modifier.weight(1f))
            }
            Row(Modifier.fillMaxWidth()) {
                Macro("FIBRAS", "${fib.fmt()}g", modifier = Modifier.weight(1f))
                Macro("SÓDIO", "${sod.toInt()}mg", modifier = Modifier.weight(1f))
                Macro("AÇÚCAR", "${acu.fmt()}g", modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun Macro(rotulo: String, valor: String, destaque: Boolean = false, modifier: Modifier = Modifier) {
    Column(modifier.padding(vertical = 8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(rotulo, fontSize = 10.sp, color = Cores.Outline, letterSpacing = 0.8.sp)
        Spacer(Modifier.height(4.dp))
        Text(
            valor,
            fontSize = if (destaque) 18.sp else 16.sp,
            fontWeight = if (destaque) FontWeight.SemiBold else FontWeight.Medium,
            color = if (destaque) Cores.OnSurface else Cores.OnSurfaceVariant,
        )
    }
}

@Composable
private fun Detalhe(rotulo: String, valor: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(rotulo, fontSize = 12.sp, color = Cores.OnSurfaceVariant)
        Text(valor, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = Cores.OnSurfaceVariant)
    }
}

@Composable
private fun DialogGramas(atual: Entrada, onCancelar: () -> Unit, onSalvar: (Double) -> Unit) {
    var texto by remember { mutableStateOf(atual.gramas.fmt()) }
    AlertDialog(
        onDismissRequest = onCancelar,
        title = { Text(atual.food.nome) },
        text = {
            OutlinedTextField(
                value = texto,
                onValueChange = { texto = it.filter(Char::isDigit).take(5) },
                label = { Text("Quantidade (g)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        },
        confirmButton = {
            TextButton(onClick = { texto.toIntOrNull()?.takeIf { it > 0 }?.let { onSalvar(it.toDouble()) } }) { Text("Salvar") }
        },
        dismissButton = { TextButton(onClick = onCancelar) { Text("Cancelar") } },
    )
}
