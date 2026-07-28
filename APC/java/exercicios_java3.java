import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// ==========================================
// 1. PADRÃO STRATEGY (Sistema de Ataques)
// ==========================================
interface AttackBehavior {
    int executeAttack();
    String getAttackName();
}

class SwordSlash implements AttackBehavior {
    public int executeAttack() { return new Random().nextInt(15) + 10; } // 10-25 dano
    public String getAttackName() { return "Corte de Espada Avançado"; }
}

class FireballSpell implements AttackBehavior {
    public int executeAttack() { return new Random().nextInt(25) + 5; } // 5-30 dano
    public String getAttackName() { return "Bola de Fogo Explosiva"; }
}

// ==========================================
// 2. ABSTRAÇÃO E POLIMORFISMO (Personagens)
// ==========================================
abstract class Character {
    protected String name;
    protected int health;
    protected int maxHealth;
    protected int defense;
    protected AttackBehavior attackBehavior;

    public Character(String name, int health, int defense, AttackBehavior attackBehavior) {
        this.name = name;
        this.health = health;
        this.maxHealth = health;
        this.defense = defense;
        this.attackBehavior = attackBehavior;
    }

    public boolean isAlive() { return this.health > 0; }
    
    public int takeDamage(int damage) {
        int actualDamage = Math.max(2, damage - this.defense);
        this.health = Math.max(0, this.health - actualDamage);
        return actualDamage;
    }

    public int attack() { return attackBehavior.executeAttack(); }
    
    public String getName() { return name; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    
    public void setAttackBehavior(AttackBehavior behavior) { this.attackBehavior = behavior; }
}

// Classes de Jogadores
class Warrior extends Character {
    public Warrior(String name) { super(name, 120, 8, new SwordSlash()); }
}

class Mage extends Character {
    public Mage(String name) { super(name, 85, 4, new FireballSpell()); }
}

// Classe de Inimigos
class Monster extends Character {
    public Monster(String name, int health, int defense, AttackBehavior behavior) {
        super(name, health, defense, behavior);
    }
}

// ==========================================
// 3. PADRÃO FACTORY (Criação de Inimigos)
// ==========================================
class EnemyFactory {
    public static Monster createEnemy(int floor) {
        Random rand = new Random();
        if (floor % 3 == 0) {
            return new Monster("Dragão Ancião (Mestre)", 150 + (floor * 10), 12, new FireballSpell());
        } else if (rand.nextBoolean()) {
            return new Monster("Goblin Saqueador", 40 + (floor * 5), 3, new SwordSlash());
        } else {
            return new Monster("Orc Guerreiro", 70 + (floor * 5), 6, new SwordSlash());
        }
    }
}

// ==========================================
// 4. ENGINE PRINCIPAL (Ciclo do Jogo)
// ==========================================
public class RpgEngine {
    private Character player;
    private int currentFloor = 1;
    private final Scanner scanner = new Scanner(System.in);

    public void start() {
        System.out.println("=== BEM-VINDO AO JAVA RPG PROFISSIONAL ===");
        initCharacter();
        gameLoop();
    }

    private void initCharacter() {
        System.out.print("Digite o nome do seu Herói: ");
        String name = scanner.nextLine();
        
        System.out.println("Escolha sua Classe:\n1. Guerreiro (Mais Vida e Defesa)\n2. Mago (Alto Dano Mágico)");
        int choice = getValidInput(1, 2);
        
        if (choice == 1) {
            player = new Warrior(name);
            System.out.println("\n-> Você escolheu o Guerreiro!");
        } else {
            player = new Mage(name);
            System.out.println("\n-> Você escolheu o Mago!");
        }
    }

    private void gameLoop() {
        while (player.isAlive()) {
            Monster enemy = EnemyFactory.createEnemy(currentFloor);
            System.out.println("\n------------------------------------------------");
            System.out.println("  MASMORRA - ANDAR " + currentFloor + " | Um " + enemy.getName() + " apareceu!");
            System.out.println("------------------------------------------------");

            while (enemy.isAlive() && player.isAlive()) {
                battleTurn(enemy);
            }

            if (player.isAlive()) {
                System.out.println("\nVocê derrotou o " + enemy.getName() + "!");
                currentFloor++;
                postBattleRecovery();
            }
        }
        System.out.println("\n=== GAME OVER ===\nVocê caiu no andar " + currentFloor + ". Tente novamente!");
    }

    private void battleTurn(Monster enemy) {
        System.out.println("\nStatus: " + player.getName() + " (" + player.getHealth() + "/" + player.getMaxHealth() + " HP) vs " 
                + enemy.getName() + " (" + enemy.getHealth() + "/" + enemy.getMaxHealth() + " HP)");
        System.out.println("1. Atacar com " + player.attackBehavior.getAttackName() + "\n2. Fugir");
        
        int action = getValidInput(1, 2);
        if (action == 2) {
            System.out.println("Você tentou fugir, mas falhou miseravelmente!");
        }

        // Turno do Jogador
        int rawDamage = player.attack();
        int finalDamage = enemy.takeDamage(rawDamage);
        System.out.println("-> Você usou " + player.attackBehavior.getAttackName() + " e causou " + finalDamage + " de dano efetivo.");

        // Turno do Inimigo
        if (enemy.isAlive()) {
            int enemyRawDamage = enemy.attack();
            int enemyFinalDamage = player.takeDamage(enemyRawDamage);
            System.out.println("-> O " + enemy.getName() + " revidou causando " + enemyFinalDamage + " de dano em você.");
        }
    }

    private void postBattleRecovery() {
        // Recupera 25% da vida máxima ao passar de andar
        int recovery = player.getMaxHealth() / 4;
        player.takeDamage(-recovery); // Dano negativo cura
        System.out.println("Você descansou brevemente e recuperou " + recovery + " de HP.");
    }

    private int getValidInput(int min, int max) {
        int input;
        while (true) {
            try {
                System.out.print("Escolha uma opção: ");
                input = Integer.parseInt(scanner.nextLine());
                if (input >= min && input <= max) break;
            } catch (NumberFormatException e) {
                // Captura entradas inválidas sem quebrar o programa
            }
            System.out.println("Opção inválida! Digite novamente.");
        }
        return input;
    }

    public static void main(String[] args) {
        new RpgEngine().start();
    }
}
