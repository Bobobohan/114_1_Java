public class SwordsMan extends Role {

    // 建構子：初始化劍士的名稱、生命值和攻擊力
    public SwordsMan(String name, int health, int attackPower) {
        super(name, health, attackPower);
    }

    // 攻擊對手（劍客 / 魔法師），父類別的參考指到子類別物件
    @Override
    public void attack(Role opponent) {
        opponent.setHealth(opponent.getHealth() - this.getAttackPower());
        System.out.println(
                this.getName() + " 揮劍攻擊 " +
                        opponent.getName() + " 造成 " +
                        this.getAttackPower() + " 點傷害。" + opponent
        );
    }
}
