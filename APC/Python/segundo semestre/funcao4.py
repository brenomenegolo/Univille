def aprovados(total_aulas, faltas, nota):
    limite_de_faltas = total_aulas * 0.25
    if faltas <= limite_de_faltas and nota >= 6:
        return 1
    else:
        return 0
total = int(input("digite o total de aulas: "))
faltas = int(input("digite o total de faltas: "))
nota = float(input("digite a nota: "))

resultado = aprovados(total, faltas, nota)
print("resultado:", resultado)
