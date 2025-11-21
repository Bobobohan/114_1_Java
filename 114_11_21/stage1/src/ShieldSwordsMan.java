public class ShieldSwordsMan extends SwordsMan {

    private int defenseCapacity;

    // 建構子：初始化盾劍士的名稱、生命值、攻擊力與防禦力
    public ShieldSwordsMan(String name, int health, int attackPower, int defenseCapacity) {
        super(name, health, attackPower);
        this.defenseCapacity = defenseCapacity;
    }

    // 攻擊對手（劍客 / 魔法師），父類別的參考指到子類別物件
    @Override
    public void attack(Role opponent) {
        int reducedDamage = this.getAttackPower() - 5; // 持盾劍士攻擊力減少 5 點
        opponent.setHealth(opponent.getHealth() - reducedDamage);
        System.out.println(
                this.getName() + " 揮劍攻擊 " +
                        opponent.getName() + " 造成 " +
                        reducedDamage + " 點傷害。" + opponent
        );
    }

    public int getDefenseCapacity() {
        return defenseCapacity;
    }

    public void defence() {
        this.setHealth(this.getHealth() + defenseCapacity);
        System.out.println(
                this.getName() + " 使用盾牌防禦，恢復 " +
                        defenseCapacity + " 點生命值。" + this
        );
    }
}
