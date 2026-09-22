import random

print("=================================")
print("       RPG - AVENTURA SOMBRIA")
print("=================================")

nome = input("Digite o nome do seu personagem: ")

vida = 100
vida_maxima = 100
ataque = 15
defesa = 5
nivel = 1
xp = 0
xp_necessario = 100
pocoes = 3

inimigos = [
    {
        "nome": "Goblin",
        "vida": 50,
        "ataque": 10,
        "defesa": 3,
        "xp": 40
    },
    {
        "nome": "Orc",
        "vida": 80,
        "ataque": 15,
        "defesa": 5,
        "xp": 60
    },
    {
        "nome": "Esqueleto",
        "vida": 65,
        "ataque": 12,
        "defesa": 4,
        "xp": 50
    }
]

def mostrar_status():
    print("\n========== STATUS ==========")
    print(f"Nome: {nome}")
    print(f"Nível: {nivel}")
    print(f"Vida: {vida}/{vida_maxima}")
    print(f"Ataque: {ataque}")
    print(f"Defesa: {defesa}")
    print(f"XP: {xp}/{xp_necessario}")
    print(f"Poções: {pocoes}")
    print("============================")

def subir_nivel():
    global nivel, xp, xp_necessario
    global vida_maxima, vida, ataque, defesa

    while xp >= xp_necessario:
        xp -= xp_necessario
        nivel += 1
        xp_necessario += 50

        vida_maxima += 20
        vida = vida_maxima
        ataque += 5
        defesa += 2

        print("\n*** VOCÊ SUBIU DE NÍVEL! ***")
        print(f"Agora você está no nível {nivel}!")
        print("Sua vida foi restaurada!")
        print(f"Ataque: {ataque}")
        print(f"Defesa: {defesa}")

def batalha(inimigo):
    global vida, xp, pocoes

    nome_inimigo = inimigo["nome"]
    vida_inimigo = inimigo["vida"]

    print(f"\nUm {nome_inimigo} apareceu!")

    while vida > 0 and vida_inimigo > 0:

        print("\n-----------------------------")
        print(f"{nome}: {vida}/{vida_maxima} HP")
        print(f"{nome_inimigo}: {vida_inimigo} HP")
        print("-----------------------------")

        print("1 - Atacar")
        print("2 - Usar poção")
        print("3 - Fugir")

        escolha = input("Escolha: ")

        if escolha == "1":

            dano = random.randint(
                ataque - 5,
                ataque + 5
            )

            dano -= inimigo["defesa"]

            if dano < 1:
                dano = 1

            critico = random.randint(1, 100)

            if critico <= 15:
                dano *= 2
                print("\n*** ATAQUE CRÍTICO! ***")

            vida_inimigo -= dano

            print(
                f"\nVocê causou {dano} de dano!"
            )

            if vida_inimigo <= 0:
                print(
                    f"\nVocê derrotou o {nome_inimigo}!"
                )

                xp_ganho = inimigo["xp"]
                xp += xp_ganho

                print(
                    f"Você ganhou {xp_ganho} XP!"
                )

                subir_nivel()

                return True

            dano_inimigo = random.randint(
                inimigo["ataque"] - 3,
                inimigo["ataque"] + 3
            )

            dano_inimigo -= defesa

            if dano_inimigo < 1:
                dano_inimigo = 1

            vida -= dano_inimigo

            print(
                f"O {nome_inimigo} causou "
                f"{dano_inimigo} de dano!"
            )

        elif escolha == "2":

            if pocoes > 0:

                cura = 30

                vida += cura

                if vida > vida_maxima:
                    vida = vida_maxima

                pocoes -= 1

                print(
                    f"\nVocê recuperou {cura} de vida!"
                )

                print(
                    f"Poções restantes: {pocoes}"
                )

            else:

                print("\nVocê não possui poções!")

        elif escolha == "3":

            chance = random.randint(1, 100)

            if chance <= 50:

                print("\nVocê conseguiu fugir!")

                return False

            else:

                print("\nVocê tentou fugir, mas falhou!")

                dano_inimigo = random.randint(
                    inimigo["ataque"] - 3,
                    inimigo["ataque"] + 3
                )

                dano_inimigo -= defesa

                if dano_inimigo < 1:
                    dano_inimigo = 1

                vida -= dano_inimigo

                print(
                    f"O inimigo causou "
                    f"{dano_inimigo} de dano!"
                )

        else:

            print("\nEscolha inválida!")

    if vida <= 0:

        print("\n================================")
        print("          VOCÊ MORREU")
        print("================================")

        return False

    return True


def chefe_final():
    global vida, xp

    chefe = {
        "nome": "Dragão das Sombras",
        "vida": 200,
        "ataque": 25,
        "defesa": 10,
        "xp": 300
    }

    print("\n================================")
    print("        CHEFE FINAL")
    print("================================")

    print(
        "Um enorme Dragão das Sombras "
        "apareceu diante de você!"
    )

    venceu = batalha(chefe)

    if venceu and vida > 0:

        print("\n================================")
        print("          VITÓRIA!")
        print("================================")

        print(
            f"{nome} derrotou o Dragão das Sombras!"
        )

        print(
            "Você se tornou uma lenda!"
        )

        return True

    return False


while True:

    if vida <= 0:
        break

    print("\n========== MENU ==========")
    print("1 - Explorar")
    print("2 - Ver status")
    print("3 - Usar poção")
    print("4 - Sair")
    print("==========================")

    escolha = input("Escolha: ")

    if escolha == "1":

        evento = random.randint(1, 100)

        if evento <= 60:

            inimigo = random.choice(inimigos)

            batalha(inimigo)

        elif evento <= 80:

            ouro = random.randint(10, 50)

            print(
                f"\nVocê encontrou "
                f"{ouro} moedas de ouro!"
            )

        elif evento <= 95:

            cura = random.randint(10, 25)

            vida += cura

            if vida > vida_maxima:
                vida = vida_maxima

            print(
                f"\nVocê encontrou uma fonte "
                f"e recuperou {cura} de vida!"
            )

        else:

            print(
                "\nVocê encontrou a entrada "
                "para a caverna do chefe!"
            )

            entrar = input(
                "Deseja enfrentar o chefe? (s/n): "
            ).lower()

            if entrar == "s":

                venceu = chefe_final()

                if venceu:
                    break

    elif escolha == "2":

        mostrar_status()

    elif escolha == "3":

        if pocoes > 0:

            cura = 30

            vida += cura

            if vida > vida_maxima:
                vida = vida_maxima

            pocoes -= 1

            print(
                f"\nVocê recuperou {cura} de vida!"
            )

        else:

            print("\nVocê não possui poções!")

    elif escolha == "4":

        print("\nObrigado por jogar!")

        break

    else:

        print("\nOpção inválida!")
        