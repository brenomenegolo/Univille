def maior_valor(a, b, c,):
    if a>= b and a>=c:
        return a 
    elif b>= a and b>=c:
        return b
    else:
        return c

numero1 = int(input("Digite um número: "))
numero2 = int(input("Digite outro número: "))
numero3 = int(input("Digite mais um número: "))
print("O maior valor é:", maior_valor(numero1, numero2, numero3))