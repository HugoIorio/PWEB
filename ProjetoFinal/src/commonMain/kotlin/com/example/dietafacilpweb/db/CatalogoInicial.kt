package com.example.dietafacilpweb.db

class AlimentoInicial( // Atributos de um alimento salvo na aplicação.
    val nome: String,
    val imagem: String,
    val calorias: Double,
    val proteinaG: Double,
    val carboidratoG: Double,
    val gorduraG: Double,
    val fibraG: Double,
    val sodioMg: Double,
    val acucarG: Double,
    val porcaoPadrao: Double,
    val unidadeMedida: String,
    val tipoUnidadePadrao: String,
)


object CatalogoInicial { // Alimentos presentes na base de dados do aplicativo. Provavelmente deverão ser adicionados mais alimentos futuramente.
    val alimentos = listOf(
        AlimentoInicial("Pão Francês", "PaoFrances.webp", 3.0, 0.08, 0.58, 0.031, 0.023, 6.48, 0.036, 50.0, "g", "unidade"),
        AlimentoInicial("Leite Integral", "Leite.webp", 0.6, 0.032, 0.045, 0.033, 0.0, 0.64, 0.045, 200.0, "ml", "copo"),
        AlimentoInicial("Café", "Cafe.webp", 0.002, 0.003, 0.0, 0.0, 0.0, 0.00004, 0.0, 75.0, "ml", "xícara"),
        AlimentoInicial("Presunto", "Presunto.webp", 1.4, 0.155, 0.0011, 0.04, 0.0, 10.0, 0.008, 20.0, "g", "fatia"),
        AlimentoInicial("Queijo Mussarela", "Mussarela.webp", 3.0, 0.23, 0.04, 0.21, 0.0, 6.0, 0.02, 20.0, "g", "fatia"),
        AlimentoInicial("Banana", "Banana.webp", 0.9, 0.01, 0.23, 0.003, 0.026, 0.01, 0.14, 100.0, "g", "unidade"),
        AlimentoInicial("Maçã", "Maca.webp", 0.52, 0.003, 0.14, 0.002, 0.024, 0.01, 0.1, 150.0, "g", "unidade"),
        AlimentoInicial("Ovo Frito", "OvoFrito.webp", 2.4, 0.156, 0.012, 0.186, 0.0, 1.66, 0.004, 50.0, "g", "unidade"),
        AlimentoInicial("Arroz Branco", "Arroz.webp", 1.28, 0.025, 0.28, 0.002, 0.016, 0.01, 0.0008, 20.0, "g", "colher"),
        AlimentoInicial("Feijão Carioca", "Feijao.webp", 0.71, 0.048, 0.136, 0.005, 0.085, 0.02, 0.005, 17.0, "g", "colher"),
        AlimentoInicial("Macarrão", "Macarrao.webp", 1.6, 0.06, 0.31, 0.01, 0.018, 1.0, 0.0056, 100.0, "g", "xícara"),
        AlimentoInicial("Peito de Frango", "PeitoFrango.webp", 1.6, 0.32, 0.0, 0.025, 0.0, 0.5, 0.0, 125.0, "g", "filé"),
        AlimentoInicial("Carne Moída", "CarneMoida.webp", 2.4, 0.26, 0.0, 0.15, 0.0, 0.9, 0.0, 20.0, "g", "colher"),
        AlimentoInicial("Alface Americana", "AlfaceAmericana.webp", 0.06, 0.0041, 0.015, 0.0011, 0.012, 0.06, 0.008, 8.0, "g", "folha"),
        AlimentoInicial("Tomate", "Tomate.webp", 0.17, 0.006, 0.039, 0.0, 0.012, 0.05, 0.025, 100.0, "g", "unidade"),
        AlimentoInicial("Chocolate Amargo", "Chocolate.webp", 5.77, 0.078, 0.459, 0.426, 0.109, 0.274, 0.24, 100.0, "g", "unidade"),
        AlimentoInicial("Paçoca Caseira", "PacocaCaseira.webp", 4.92, 0.2, 0.4, 0.33, 0.052, 1.99, 0.35, 60.0, "g", "unidade"),
        AlimentoInicial("Mamão", "Mamao.webp", 0.54, 0.0, 0.0, 0.005, 0.022, 0.02, 0.118, 118.0, "g", "unidade"),
        AlimentoInicial("Água", "Agua.webp", 0.0, 0.0, 0.0, 0.0, 0.0, 0.006, 0.0, 200.0, "ml", "copo"),
        AlimentoInicial("Suco de Laranja", "SucoLaranja.webp", 0.45, 0.007, 0.104, 0.002, 0.002, 0.01, 0.084, 200.0, "ml", "copo"),
        AlimentoInicial("Pão de Forma", "PaoForma.webp", 2.54, 0.084, 0.494, 0.028, 0.028, 0.02, 0.036, 25.0, "g", "fatia"),
        AlimentoInicial("Ovo Cozido", "OvoCozido.webp", 1.46, 0.126, 0.006, 0.096, 0.0, 1.46, 0.006, 50.0, "g", "unidade"),
        AlimentoInicial("Omelete", "Omelete.webp", 1.7, 0.106, 0.008, 0.138, 0.0, 3.2, 0.005, 100.0, "g", "unidade"),
        AlimentoInicial("Queijo Fresco", "QueijoFresco.webp", 2.63, 0.173, 0.033, 0.2, 0.0, 3.1, 0.03, 30.0, "g", "fatia"),
        AlimentoInicial("Suco de Uva", "SucoUva.webp", 0.6, 0.003, 0.147, 0.0, 0.002, 0.04, 0.14, 200.0, "ml", "copo"),
        AlimentoInicial("Milho Cozido", "MilhoCozido.webp", 0.96, 0.032, 0.19, 0.015, 0.024, 0.01, 0.032, 100.0, "g", "xícara"),
        AlimentoInicial("Batata Cozida", "BatataCozida.webp", 0.86, 0.019, 0.201, 0.001, 0.018, 0.02, 0.008, 150.0, "g", "unidade"),
        AlimentoInicial("Aveia em Flocos", "AveiaFlocos.webp", 3.94, 0.139, 0.666, 0.085, 0.091, 0.04, 0.01, 10.0, "g", "colher"),
        AlimentoInicial("Iogurte Natural", "IogurteNatural.webp", 0.61, 0.041, 0.046, 0.03, 0.0, 0.55, 0.046, 15.0, "g", "colher"),
        AlimentoInicial("Abacate", "Abacate.webp", 1.6, 0.02, 0.085, 0.147, 0.067, 0.07, 0.007, 35.0, "g", "colher"),
    )

    val refeicoes = listOf("Café da Manhã", "Almoço", "Lanche da Tarde", "Jantar") // Refeições padrão (serão criadas ao inicialziar o aplicativo)
}
