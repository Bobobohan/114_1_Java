/**
 * Template Method Pattern 示範程式
 * 展示模板方法、抽象方法和 Hook Method 的使用
 */

// 抽象類別：定義模板方法
abstract class Character {
    private String name;
    private int health;
    
    public Character(String name, int health) {
        this.name = name;
        this.health = health;
    }
    
    public String getName() { return name; }
    public int getHealth() { return health; }
    public boolean isAlive() { return health > 0; }
    
    public void takeDamage(int damage) {
        health -= damage;
        System.out.println("💥 " + name + " 受到 " + damage + " 點傷害！剩餘HP: " + health);
    }
    
    // ========== Template Method Pattern ==========
    
    /**
     * 模板方法：定義戰鬥流程
     * final 確保流程不被修改
     */
    public final void performBattle(Character opponent) {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("  ⚔️  " + name + " vs " + opponent.getName());
        System.out.println("╚════════════════════════════════════╝");
        
        // 步驟 1：戰前檢查（具體方法）
        if (!preBattleCheck(opponent)) {
            System.out.println("❌ 戰鬥無法進行！\n");
            return;
        }
        
        // 步驟 2：戰鬥準備（抽象方法）
        prepare();
        
        // 步驟 3：攻擊前行為（Hook Method）
        beforeAttack(opponent);
        
        // 步驟 4：執行攻擊（抽象方法）
        attack(opponent);
        
        // 步驟 5：攻擊後行為（Hook Method）
        afterAttack(opponent);
        
        System.out.println("────────────────────────────────────\n");
    }
    
    // 具體方法：所有角色相同的邏輯
    private boolean preBattleCheck(Character opponent) {
        if (!this.isAlive() || !opponent.isAlive()) {
            return false;
        }
        return true;
    }
    
    // 抽象方法：必須由子類別實作
    protected abstract void prepare();
    protected abstract void attack(Character opponent);
    
    // Hook Method：子類別可選擇性覆寫
    protected void beforeAttack(Character opponent) {
        // 預設什麼都不做
    }
    
    protected void afterAttack(Character opponent) {
        // 預設什麼都不做
    }
}

// 劍士：實作抽象方法，覆寫 Hook Method
class Warrior extends Character {
    public Warrior(String name, int health) {
        super(name, health);
    }
    
    @Override
    protected void prepare() {
        System.out.println("⚔️  " + getName() + " 擦拭劍刃...");
    }
    
    @Override
    protected void attack(Character opponent) {
        System.out.println("⚔️  " + getName() + " 揮劍攻擊！");
        opponent.takeDamage(20);
    }
    
    // 覆寫 Hook Method
    @Override
    protected void beforeAttack(Character opponent) {
        System.out.println("🔊 " + getName() + "：「受死吧！」");
    }
    
    @Override
    protected void afterAttack(Character opponent) {
        if (opponent.isAlive()) {
            System.out.println("⚔️  " + getName() + " 擺出防禦架式...");
        }
    }
}

// 法師：實作抽象方法，不覆寫 Hook Method
class Mage extends Character {
    public Mage(String name, int health) {
        super(name, health);
    }
    
    @Override
    protected void prepare() {
        System.out.println("✨ " + getName() + " 吟唱咒語...");
    }
    
    @Override
    protected void attack(Character opponent) {
        System.out.println("✨ " + getName() + " 施放魔法！");
        opponent.takeDamage(25);
    }
    
    // 不覆寫 Hook Method，使用預設空實作
}

// 主程式：展示模板方法的使用
public class TemplateMethodDemo {
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════");
        System.out.println("    Template Method Pattern 展示");
        System.out.println("════════════════════════════════════════\n");
        
        Warrior warrior = new Warrior("勇敢的劍士", 100);
        Mage mage = new Mage("智慧的法師", 80);
        
        System.out.println("【展示 1：劍士攻擊】");
        System.out.println("劍士覆寫了 Hook Method，會有攻擊前後的特殊行為");
        warrior.performBattle(mage);
        
        System.out.println("【展示 2：法師攻擊】");
        System.out.println("法師沒有覆寫 Hook Method，不會有特殊行為");
        mage.performBattle(warrior);
        
        System.out.println("\n════════════════════════════════════════");
        System.out.println("重點觀察：");
        System.out.println("1. 戰鬥流程完全相同（由模板方法控制）");
        System.out.println("2. 準備和攻擊方式不同（抽象方法實作）");
        System.out.println("3. 劍士有攻擊前後行為（覆寫 Hook Method）");
        System.out.println("4. 法師沒有攻擊前後行為（使用預設 Hook）");
        System.out.println("════════════════════════════════════════");
    }
}
