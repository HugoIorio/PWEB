package com.example.dietafacilpweb

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

// Esse é o arquivo que exibe detalhes de um alimento selecionado após a pesquisa
@Composable
fun DetalheAlimentoScreen(
    food: Food,
    onVoltar: () -> Unit,
    onConfirmar: (Double) -> Unit,
) {
    var texto by remember(food) { mutableStateOf((food.porcao ?: 100.0).fmt()) }
    val quantidade = texto.replace(',', '.').toDoubleOrNull() ?: 0.0
    val valida = quantidade > 0.0
    val porcao = Entrada(food, quantidade) // calcula os nutrientes para a quantidade informada

    Column(Modifier.fillMaxSize()) {
        AppBar(titulo = "Detalhes do Alimento", onVoltar = onVoltar)
        Column(Modifier.weight(1f).verticalScroll(rememberScrollState())) {
            CabecalhoImagem(food)
            Column(
                Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
            ) {
                Column {
                    Text(food.nome, fontSize = 36.sp, fontWeight = FontWeight.Bold, color = Cores.Primary)
                    Text("${porcao.kcal.br()} calorias", fontSize = 24.sp, fontWeight = FontWeight.Medium, color = Cores.OnSurface)
                }

                Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = texto,
                        onValueChange = { texto = it.filter { c -> c.isDigit() || c == ',' || c == '.' }.take(7) },
                        label = { Text("Quantidade Consumida (${food.unidade})") },
                        singleLine = true,
                        isError = !valida,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.weight(1f),
                    )
                    correspondencia(food, quantidade)?.let { texto ->
                        Column(
                            Modifier.weight(1f).padding(start = 16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text("Corresponde a", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Cores.OnSurface, textAlign = TextAlign.Center)
                            Text(texto, fontSize = 18.sp, fontWeight = FontWeight.Medium, color = Cores.OnSurface, textAlign = TextAlign.Center)
                        }
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text("Informações Nutricionais", fontSize = 22.sp, fontWeight = FontWeight.Medium, color = Cores.OnSurface)
                    LinhaNutriente("Calorias", porcao.kcal.br())
                    LinhaNutriente("Proteínas", "${porcao.proteina.br()}g")
                    LinhaNutriente("Carboidratos", "${porcao.carbo.br()}g")
                    LinhaNutriente("Gorduras", "${porcao.gordura.br()}g")
                    LinhaNutriente("Fibras", "${porcao.fibra.br()}g")
                    LinhaNutriente("Sódio", "${porcao.sodioMg.roundToInt()}mg")
                    LinhaNutriente("Açúcar", "${porcao.acucar.br()}g")
                }
                Spacer(Modifier.height(8.dp))
            }
        }

        Box(Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp)) {
            Button(
                onClick = { onConfirmar(quantidade) },
                enabled = valida,
                modifier = Modifier.fillMaxWidth().height(64.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Cores.Primary),
            ) {
                Text("Confirmar Adição", fontSize = 22.sp, fontWeight = FontWeight.Medium)
            }
        }
    }
}

// Foto desfocada ao fundo, com um gradiente
@Composable
private fun CabecalhoImagem(food: Food) {
    val imagem by produceState<ImageBitmap?>(initialValue = null, food.imagem) {
        value = food.imagem?.let { carregarImagem(it) }
    }
    Box(
        Modifier.fillMaxWidth().height(220.dp).clipToBounds().background(Cores.PrimaryFixed.copy(alpha = 0.3f)),
    ) {
        val bmp = imagem
        if (bmp != null) {
            Image(
                bitmap = bmp,
                contentDescription = food.nome,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize().blur(12.dp),
            )
        } else {
            Text(food.emoji, fontSize = 80.sp, modifier = Modifier.align(Alignment.Center))
        }
        Box(
            Modifier.fillMaxSize().background(
                Brush.verticalGradient(listOf(Color.Black.copy(alpha = 0.25f), Color.White)),
            ),
        )
    }
}

@Composable
private fun LinhaNutriente(rotulo: String, valor: String) {
    Row(Modifier.fillMaxWidth().padding(horizontal = 12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(rotulo, fontSize = 18.sp, color = Cores.OnSurface)
        Text(valor, fontSize = 18.sp, color = Cores.OnSurface)
    }
}

// Função para encontrar correspondência entre g/ml informados e quantas unidades isso representa
private fun correspondencia(food: Food, quantidade: Double): String? {
    val porcao = food.porcao?.takeIf { it > 0.0 } ?: return null
    val tipo = food.tipoUnidade ?: return null
    val n = quantidade / porcao
    return "${n.br2()} ${if (n <= 1.0) tipo else plural(tipo)}"
}

private fun plural(palavra: String) = if (palavra.endsWith("r")) palavra + "es" else palavra + "s"

// Função para formatar decimal com apenas 1 casa
private fun Double.br() = fmt().replace('.', ',')

// Função para formatar decimal até duas casas
private fun Double.br2(): String {
    val r = (this * 100).roundToInt() / 100.0
    return if (r % 1.0 == 0.0) r.toInt().toString() else r.toString().replace('.', ',')
}
