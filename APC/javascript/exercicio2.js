let disciplinas = [
    "HTML",
    "CSS",
    "JavaScript"
];

console.log("Disciplinas:");

disciplinas.forEach(disciplina => {
    console.log(disciplina);
});

console.log(`Total: ${disciplinas.length}`);

if (disciplinas.includes("JavaScript")) {
    console.log("Aluno cursa JavaScript");
} else {
    console.log("Aluno não cursa JavaScript");
}

disciplinas.push("Algoritmos");

console.log("\nPercorrendo disciplinas:");

for (let i = 0; i < disciplinas.length; i++) {

    if (disciplinas[i] === "") {
        continue;
    }

    console.log(disciplinas[i]);

    if (disciplinas[i] === "TCC") {
        console.log("Laço interrompido");
        break;
    }
}