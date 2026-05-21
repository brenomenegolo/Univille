for i in range(15):
    nota = float(input("Digite uma nota entre 0 e 5: "))

    while nota < 0 or nota > 5:
        print("Nota inválida!")
        nota = float(input("Digite novamente: "))

    print("Nota válida:", nota)




# exercio 