gabarito = []
classificacao = []

with open("gabarito.txt", "r", encoding="utf-8") as arquivo_gabarito:
    conteudo = arquivo_gabarito.read()
    gabarito = conteudo.strip().split(",")

with open("candidatos.txt", "r", encoding="utf-8") as arquivo_candidatos:

    for linha in arquivo_candidatos:

        linha = linha.strip()

        if linha == "":
            continue

        dados = linha.split(",")

        id_candidato = dados[0]
        nome = dados[1]
        respostas = dados[2:]

        print(f"ID: {id_candidato}, Nome: {nome}, Respostas: {respostas}")

        nota = 0

        for i in range(len(gabarito)):
            if respostas[i] == gabarito[i]:
                nota += 1

        classificacao.append([id_candidato, nome, nota])

with open("classificacao.txt", "w", encoding="utf-8") as arquivo_saida:
    for candidato in classificacao:
        arquivo_saida.write(f"{candidato[0]},{candidato[1]},{candidato[2]}\n")
    print("Correção realizada com sucesso!")


