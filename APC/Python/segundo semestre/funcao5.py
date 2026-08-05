def classe_eleitoral(idade):
    if idade < 16:
        print("Não eleitor")
    elif (idade >= 16 and idade < 18) or idade > 65:
        print("Eleitor facultativo")
    else:
        print("Eleitor obrigatório")
idade = int(input("Digite a idade: "))
classe_eleitoral(idade)