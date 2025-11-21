public class Magician extends Role {

    // 治療力
    private int healPower;

    // 建構子：初始化魔法師的名稱、生命值、攻擊力與治療力
    public Magician(String name, int health, int attackPower, int healPower) {
        super(name, health, attackPower);
        this.healPower = healPower;
    }

    // 取得治療力
    public int getHealPower() { return healPower; }

    // 攻擊對手（施展魔攻） - 父類別的參考呼叫子類別
    @Override
    public void attack(Role opponent) {
        opponent.setHealth(opponent.getHealth() - this.getAttackPower());
        System.out.println(this.getName() + " 發動攻擊 " + opponent.getName() + " 造成 "
                + this.getAttackPower() + " 點傷害！" + opponent);
    }

    // 治療隊友（施展魔法治療） - 父類別的參考呼叫子類別
    public void heal(Role ally) {
        ally.setHealth(ally.getHealth() + this.healPower);
        System.out.println(this.getName() + " 治療 " + ally.getName() +
                "，回復 " + healPower + " 點生命值，" + ally);
    }

    @Override
    public String toString() {
        return super.toString() + "，治療力：" + healPower;
    }
}
