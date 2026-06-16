#1
gastos = [2172.54, 3701.35, 3518.09, 3456.61, 3249.38,
          2840.82, 3891.45, 3075.26, 2317.64, 3219.08]

media = sum(gastos) / len(gastos)

print("Média dos gastos:", media)

#2
gastos = [2172.54, 3701.35, 3518.09, 3456.61, 3249.38,
          2840.82, 3891.45, 3075.26, 2317.64, 3219.08]

acima_3000 = 0

for valor in gastos:
    if valor > 3000:
        acima_3000 += 1

porcentagem = (acima_3000 / len(gastos)) * 100

print("Compras acima de 3000:", acima_3000)
print("Porcentagem:", porcentagem, "%")

#3
numeros = []

for i in range(5):
    num = int(input("Digite um número: "))
    numeros.append(num)

print(numeros)

#4
numeros = []

for i in range(5):
    num = int(input("Digite um número: "))
    numeros.append(num)

print("Lista original:", numeros)
print("Lista invertida:", numeros[::-1])

#5
numero = int(input("Digite um número: "))

primos = []

for n in range(2, numero + 1):
    primo = True

    for i in range(2, n):
        if n % i == 0:
            primo = False
            break

    if primo:
        primos.append(n)

print(primos)

#6
doces = 0
amargos = 0

for i in range(10):
    id_produto = int(input("Digite o ID: "))

    if id_produto % 2 == 0:
        doces += 1
    else:
        amargos += 1

print("Produtos doces:", doces)
print("Produtos amargos:", amargos)

#7
gabarito = ["D", "A", "C", "B", "A",
            "D", "C", "C", "A", "B"]

nota = 0

for i in range(10):
    resposta = input(f"Questão {i+1}: ").upper()

    if resposta == gabarito[i]:
        nota += 1

print("Nota final:", nota)

#8
notas = []

while True:
    nota = float(input("Digite uma nota (-1 para sair): "))

    if nota == -1:
        break

    notas.append(nota)

print("Quantidade:", len(notas))

print("\nOrdem normal:")
for nota in notas:
    print(nota)

print("\nOrdem inversa:")
for nota in notas[::-1]:
    print(nota)

soma = sum(notas)
media = soma / len(notas)

print("\nSoma:", soma)
print("Média:", media)

acima_media = 0

for nota in notas:
    if nota > media:
        acima_media += 1

print("Notas acima da média:", acima_media)

#9
idades = []

for i in range(8):
    idade = int(input("Digite uma idade: "))
    idades.append(idade)

maior = max(idades)
menor = min(idades)

posicao = idades.index(maior)

print("Maior idade:", maior)
print("Menor idade:", menor)
print("Posição da maior idade:", posicao)

#10
precos = [12.50, 99.00, 45.00, 5.90, 150.00, 25.00]

filtrados = []

for preco in precos:
    if preco > 30:
        desconto = preco * 0.90
        filtrados.append(desconto)

print("Produtos com desconto:")
print(filtrados)