c1 = 0
c2 = 0
c3 = 0
c4 = 0
nulos = 0
brancos = 0

for i in range(20):

    voto = int(input("Digite seu voto: "))

    if voto == 1:
        c1 += 1

    elif voto == 2:
        c2 += 1

    elif voto == 3:
        c3 += 1

    elif voto == 4:
        c4 += 1

    elif voto == 5:
        nulos += 1

    elif voto == 6:
        brancos += 1

total = 20

print("Candidato 1:", c1)
print("Candidato 2:", c2)
print("Candidato 3:", c3)
print("Candidato 4:", c4)
print("Nulos:", nulos)
print("Brancos:", brancos)

print("Porcentagem de nulos:", (nulos / total) * 100, "%")
print("Porcentagem de brancos:", (brancos / total) * 100, "%")