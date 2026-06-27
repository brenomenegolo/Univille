const gastos = [
    "500",
    "350.50",
    "1000"
];

function calcularTotal(lista) {

    let total = 0;

    for (let valor of lista) {

        let numero = Number(valor);

        if (isNaN(numero)) {
            continue;
        }

        total += numero;
    }

    return total;
}

const total = calcularTotal(gastos);

console.log(`Total: R$ ${total}`);

if (total > 2000) {
    console.log("Limite ultrapassado");
} else {
    console.log("Gastos dentro do limite");
}