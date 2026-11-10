package com.example.dietafacilpweb

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dietafacilpweb.shared.generated.resources.Res
import kotlin.coroutines.cancellation.CancellationException
import org.jetbrains.compose.resources.decodeToImageBitmap

// Especificação de onde fica a pasta com as imagens dos alimentos
private const val PASTA_IMAGENS = "files"

// Imagens decodificadas
private val cacheImagens = mutableMapOf<String, ImageBitmap>()

internal suspend fun carregarImagem(nome: String): ImageBitmap? {
    cacheImagens[nome]?.let { return it }
    return try {
        Res.readBytes("$PASTA_IMAGENS/$nome").decodeToImageBitmap().also { cacheImagens[nome] = it }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        // Arquivo ausente ou ilegível: o chamador mostra o ícone alternativo.
        println("ImagemAlimento: não foi possível carregar '$PASTA_IMAGENS/$nome' -> ${e::class.simpleName}: ${e.message}")
        null
    }
}

// Foto do alimento
@Composable
fun ImagemAlimento(food: Food, fundo: Color) {
    val imagem by produceState<ImageBitmap?>(initialValue = food.imagem?.let { cacheImagens[it] }, food.imagem) {
        value = food.imagem?.let { carregarImagem(it) }
    }
    Box(
        Modifier.size(48.dp).clip(RoundedCornerShape(8.dp)).background(fundo),
        contentAlignment = Alignment.Center,
    ) {
        val bmp = imagem
        if (bmp != null) {
            Image(
                bitmap = bmp,
                contentDescription = food.nome,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            Text(food.emoji, fontSize = 24.sp)
        }
    }
}
