// Esse arquivo descreve o layout de componentes padrozinados da aplicação

package com.example.dietafacilpweb

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BotaoIcone(texto: String, cor: Color = Cores.Primary, onClick: () -> Unit) {
    Box(
        Modifier.size(40.dp).clip(CircleShape).clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) { Text(texto, fontSize = 20.sp, color = cor) }
}

@Composable
fun AppBar(
    titulo: String,
    onVoltar: (() -> Unit)? = null,
    acao: String? = null,
    onAcao: () -> Unit = {},
) {
    Surface(color = Cores.Surface, shadowElevation = 2.dp) {
        Row(
            Modifier.statusBarsPadding().fillMaxWidth().height(64.dp).padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (onVoltar != null) BotaoIcone("←", onClick = onVoltar)
            Text(
                titulo,
                color = Cores.Primary,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
            )
            if (acao != null) BotaoIcone(acao, onClick = onAcao)
        }
    }
}

@Composable
fun BotaoPilula(texto: String, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Row(
        modifier
            .clip(RoundedCornerShape(50))
            .background(Cores.Primary)
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(texto, color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun IconeQuadrado(fundo: Color = Cores.Primary.copy(alpha = 0.1f)) {
    Box(
        Modifier.size(48.dp).clip(RoundedCornerShape(8.dp)).background(fundo),
        contentAlignment = Alignment.Center,
    ) { Text("", fontSize = 24.sp) }
}
