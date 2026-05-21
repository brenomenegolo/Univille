idade = int(input("digite sua idade: "))
maior = idade
menor = idade
while idade >= 0:
    idade = int(input("digite sua idade: "))
    if idade > maior:
        maior = idade
    if idade < menor and idade >= 0:
        menor = idade
if maior <0 or menor <0:
    print("Nenhuma idade foi informada!")
print("Média Aritimética: ", (maior + menor) / 2)