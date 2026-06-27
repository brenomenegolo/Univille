const produto = {
    nome: "Monitor",
    categoria: "Informática",
    preco: "899.90",
    estoque: 5
};

produto.preco = Number(produto.preco);

if (isNaN(produto.preco)) {
    console.log("Preço inválido.");
} else {

    function exibirProduto(prod) {
        console.log(`Produto: ${prod.nome}`);
        console.log(`Categoria: ${prod.categoria}`);
        console.log(`Preço: R$ ${prod.preco}`);
    }

    exibirProduto(produto);

    if (produto.estoque < 10) {
        console.log("Estoque baixo");
    } else {
        console.log("Estoque adequado");
    }

    console.log(Object.keys(produto));

    console.log("\nPropriedades e valores:");
    for (let chave in produto) {
        console.log(`${chave}: ${produto[chave]}`);
    }

    console.log("\nTipos:");
    for (let chave in produto) {
        console.log(`${chave}: ${typeof produto[chave]}`);
    }
}