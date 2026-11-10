package com.example.dietafacilpweb

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Tela de pesquisa de alimentos
@Composable
fun PesquisaScreen(
    onVoltar: () -> Unit,
    onAdicionar: (Food) -> Unit,
    sugestoes: List<Food>,
    buscar: (String) -> List<Food>,
) {
    var busca by remember { mutableStateOf("") }
    val lista = remember(busca) { if (busca.isBlank()) sugestoes else buscar(busca) }

    Column(Modifier.fillMaxSize()) {
        AppBar(titulo = "Adicionar alimento a refeição", onVoltar = onVoltar, acao = "🔍")
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp, vertical = 24.dp),
        ) {
            Surface(
                color = Cores.SurfaceLow,
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Cores.OutlineVariant),
                shadowElevation = 1.dp,
            ) {
                Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("🔍", fontSize = 18.sp)
                    TextField(
                        value = busca,
                        onValueChange = { busca = it },
                        placeholder = { Text("Buscar alimento...", color = Cores.OnSurfaceVariant) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                    )
                }
            }
            Spacer(Modifier.height(40.dp))

            Text(
                if (busca.isBlank()) "SUGESTÕES COMUNS" else "RESULTADOS",
                fontSize = 14.sp, fontWeight = FontWeight.SemiBold,
                color = Cores.OnSurfaceVariant, letterSpacing = 1.sp,
            )
            Spacer(Modifier.height(8.dp))
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                lista.forEach { CartaoAlimento(it) { onAdicionar(it) } }
                if (lista.isEmpty()) {
                    Text("Nenhum alimento encontrado.", color = Cores.OnSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun CartaoAlimento(food: Food, onAdd: () -> Unit) {
    Surface(
        color = Cores.SurfaceLowest,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Cores.OutlineVariant),
        shadowElevation = 1.dp,
    ) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            ImagemAlimento(food, Cores.PrimaryFixed.copy(alpha = 0.3f))
            Column(Modifier.weight(1f).padding(horizontal = 24.dp)) {
                Text(food.nome, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Cores.OnSurface)
                Text("${(food.kcal * food.porcao).toInt()} kcal por ${food.tipoUnidade}", fontSize = 12.sp, color = Cores.OnSurfaceVariant)
            }
            Box(
                Modifier.size(44.dp).clip(CircleShape).background(Cores.PrimaryFixed).clickable(onClick = onAdd),
                contentAlignment = Alignment.Center,
            ) { Text("＋", fontSize = 22.sp, color = Cores.OnPrimaryFixedVariant) }
        }
    }
}
