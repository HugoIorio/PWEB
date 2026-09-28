var num1 = parseInt(prompt("Informe o primeiro número."));
var num2 = parseInt(prompt("Informe o segundo número."));

document.write("Primeiro número: " + num1);
document.write("<br>Segundo número: " + num2);

document.write("<br><br>Soma dos dois: " + (num1 + num2));
document.write("<br>Subração do primeiro pelo segundo: " + (num1 - num2));
document.write("<br>Produto dos dois: " + (num1 * num2));
document.write("<br>Divisão do primeiro pelo segundo: " + Math.floor(num1 / num2));
document.write("<br>Resto da divisão do primeiro pelo segundo: " + (num1 % num2));
