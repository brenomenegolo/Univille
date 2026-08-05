def par(numero):
    if numero % 2 == 0:
        return True
    else:
        return False
num = int(input("Digite um número: "))
print(par(num))
