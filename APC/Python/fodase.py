alunos = [ ["Joaquim Pietro Salvador", 18, "joaquimlennertz@gmail.com"], 
           ["Pedro Henrique Lima", 18, "pedrolimaa2007@gmail.com"],
           ["Breno Teichert Menegolo", 18, "brenomenegolopc@gmail.com"] ]





with open("alunos.txt", "r") as arquivo: 
    conteudo = arquivo.read() 
    conteudo = conteudo.replace("João", "Pedro") 
    with open("alunos.txt", "w") as arquivo: 
        arquivo.write(conteudo) 





# # Salvando a matriz
# with open("alunos.txt", "w") as file:
#     for linha in alunos:
#         file.write(" ".join(map(str, linha)) + "\n")
#     file.write("-" * 20 + "\n")
    


# with open("matriz.txt", "r", encoding="utf-8") as arquivo:
#     conteudo = arquivo.read()
#     print(conteudo)
