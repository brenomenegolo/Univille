codigo = int(input("Insira o código (1 = enfermeiro), (2 = Nutricionista), (3 = Médico)"))
print("Para sair digite 0")
soma = 0 
qnt = 0 
while codigo != 0:
    salario = float(input("Insira o salario: "))
    if codigo == 3:
        if salario < 4500:
            soma += salario
            qnt += 1
            codigo = int(input("Insira o código (1 = enfermeiro), (2 = Nutricionista), (3 = Médico)"))
if qnt > 0:
    media = soma / qnt
    print("A média salarial dos médicos é: ", media)
else:
    print("Nenhum médico foi informado!")