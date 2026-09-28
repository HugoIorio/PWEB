var nome = prompt("Insira o nome do aluno: ");

var nota1 = parseFloat(prompt("Insira a nota da 1º prova: "));
var nota2 = parseFloat(prompt("Insira a nota da 2º prova: "));
var nota3 = parseFloat(prompt("Insira a nota da 3º prova: "));
var nota4 = parseFloat(prompt("Insira a nota da 4º prova: "));


var media = (nota1 + nota2 + nota3 + nota4)/4

document.write("Nome do aluno: " + nome);
document.write("<br><br>Média aritmética das provas: " + media.toFixed(2));