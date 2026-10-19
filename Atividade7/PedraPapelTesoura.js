let jog1 = prompt("Selecione sua jogada: \n\nPedra\nPapel\nTesoura");
let jog2;

let aleatorio = Math.round((Math.random() * 100))%3;

switch(aleatorio) {
    case 0:
        jog2 = "pedra";
        break;
    case 1:
        jog2 = "papel";
        break;
    case 2:
        jog2 = "tesoura";
        break;
    deafult:
        jog2 = "erro";
        break;
}

jog1 = jog1.toLowerCase();

if((jog1 == "pedra" && jog2 == "tesoura") || (jog1 == "papel" && jog2 == "pedra") || (jog1 == "tesoura" && jog2 == "papel")) {
    alert("Jogador venceu!\n\nJogador jogou " + jog1 + "\nComputador jogou " + jog2);
}
else if((jog2 == "pedra" && jog1 == "tesoura") || (jog2 == "papel" && jog1 == "pedra") || (jog2 == "tesoura" && jog1 == "papel")) {
    alert("Computador venceu!\n\nJogador jogou " + jog1 + "\nComputador jogou " + jog2);
}
else if (jog1 == jog2){
    alert("Empate!\n\nJogador jogou " + jog1 + "\nComputador jogou " + jog2)
}
else {
    alert("Erro!\n\nVocê jogou " + jog1 + ", que não é uma opção válida.")
}
