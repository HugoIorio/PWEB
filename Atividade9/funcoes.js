// Função 1 - Recebe 3 números e retorna o maior
function maiorNumero(num1, num2, num3) {
    return Math.max(num1, num2, num3);
}

// Função 2 - Recebe 3 números e os retorna em ordem crescente
function ordemCrescente(num1, num2, num3) {
    let numeros = [num1, num2, num3];
    numeros.sort(function(a, b){return a-b}); // O Array.prototype.sort do JavaScript ordena por conversão de string, então é necessário passar uma função de comparação como parâmetro. Se o resultado desta função é negativo, a fica antes de b. Se for positivo, b fica antes de a. Se for == 0, mantém a ordem.
    return numeros;
}

// Função 3 - Recebe uma palavra e retorna se é ou não palíndromo (igual de trás pra frente)
function verificarPalindromo(palavra) {
    let inversa = "";
    palavra = palavra.toLowerCase();

    for (let i = palavra.length - 1; i >= 0; i--) { // Construir palavra inversa, copiando caracteres da palavra original do maior índice para o menor
        inversa += palavra[i];
    }
    if (palavra == inversa)
        return "É palíndromo";
    else
        return "Não é palíndromo";
}

/* Função 4 - Recebe duas palavras; retorna "Erro" se pelo menos uma for vazia ou undefined;
Deve retornar "É um subconjunto" se a segunda palavra for um subconjunto (contida) da primeira palavra;
Deve retornar "Não é um subconjunto" se a segunda palavra não for um subconjunto da primeira palavra;
*/
function verificarSubconjunto(pal1, pal2) {
    if(!pal1 || !pal2) {
        return "Erro";
    }
    pal1 = pal1.toLowerCase();
    pal2 = pal2.toLowerCase();
    if(pal1.includes(pal2)){
        return "É um subconjunto";
    }
    else {
        return "Não é um subconjunto";
    }
}

// Função 5 - Recebe uma data e retorna o dia da semana
function diaDaSemana(data) {
    if(!(data instanceof Date)){ // Verificar se data é um objeto da classe Date
        return "Erro ao obter data (inválida)"
    }
    else {
        switch (data.getDay()) {
            case 0:
                return "Domingo";
            case 1:
                return "Segunda-feira";
            case 2:
                return "Terça-feira";
            case 3:
                return "Quarta-feira";
            case 4:
                return "Quinta-feira";
            case 5:
                return "Sexta-feira";
            case 6:
                return "Sábado";
            default:
                return "Erro ao obter dia";
        }
    }
}

// Executando as funções

// Função 1 e 2
let val1 = prompt("Digite o primeiro número: ", 0);
let val2 = prompt("Digite o segundo numero: ", 0);
let val3 = prompt("Digite o terceiro número: ", 0);

window.alert("Maior número: " + maiorNumero(val1, val2, val3) + "\nNúmeros em ordem crescente: " + ordemCrescente(val1, val2, val3));

// Função 3
let palavra = prompt("Digite uma palavra para verificar se ela é um palíndromo: ");
window.alert(verificarPalindromo(palavra));

// Função 4
let pal1 = prompt("Digite uma palavra: ");
let pal2 = prompt("Digite outra palavra: ");
window.alert(verificarSubconjunto(pal1, pal2));

// Função 5
let dia = prompt("Digite o dia de uma data: ");
let mes = prompt("Digite o mês de uma data: ");
let ano = prompt("Digite o ano de uma data: ");
let d = new Date(ano, (mes - 1), dia);
window.alert(dia + "/" + mes + "/" + ano + " caiu em um(a) " + diaDaSemana(d));
