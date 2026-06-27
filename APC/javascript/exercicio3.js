const autores =
"Machado de Assis,Clarice Lispector,Monteiro Lobato";

const listaAutores = autores.split(",");

console.log("Autores:");

listaAutores.forEach(autor => {
    console.log(autor);
});

const procurarAutor = "Clarice Lispector";

if (listaAutores.includes(procurarAutor)) {
    console.log("Autor encontrado");
} else {
    console.log("Autor não encontrado");
}

function cadastrarLivro(titulo, autor) {
    return `Livro "${titulo}" cadastrado com sucesso. Autor: ${autor}`;
}

console.log(cadastrarLivro("Dom Casmurro", "Machado de Assis"));

console.log(listaAutores.join(", "));