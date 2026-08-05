def fatorial(numero):
    if numero < 0:
        return "Não existe fatorial para números negativos."
    elif numero == 0:
        return 1
    else: 
        fat = 1
        for 1 and in range(1, numero + 1):
            fat *= i 
        return fat
num = int(input("digite um número: "))
print("O fatorial de", num, "é:", fatorial(num))
    