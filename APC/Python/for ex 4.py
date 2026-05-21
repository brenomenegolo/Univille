soma = 0
quantidade = 0

temperatura = float(input("Digite a temperatura: "))

while temperatura != -273:
    soma += temperatura
    quantidade += 1

    temperatura = float(input("Digite a temperatura: "))

media = soma / quantidade

print("Média das temperaturas:", media)