public class SwordsMan extends Role {

    // 建構子：初始化劍士的名稱、生命值與攻擊力
    public SwordsMan(String name, int health, int attackPower) {
        super(name, health, attackPower);
    }

    // ======== 一般攻擊方法 ========
    @Override
    public void attack(Role opponent) {
        opponent.setHealth(opponent.getHealth() - this.getAttackPower());
        System.out.println(
                this.getName() + " 揮劍攻擊 " +
                        opponent.getName() + " 造成 " +
                        this.getAttackPower() + " 點傷害。" + opponent
        );
    }

    // ======== 特殊技能顯示方法 ========
    /**
     * 劍士的特殊技能：連續斬擊
     * 這裡僅示範顯示，不包含實際傷害計算
     */
    @Override
    public void showSpecialSkill() {
        System.out.println();
        System.out.println("【" + this.getName() + " 的特殊技能】");
        System.out.println("=================================");
        System.out.println("技能名稱：連續斬擊");
        System.out.println("技能描述：快速揮劍斬擊三次");
        System.out.println("技能效果：造成 150% 傷害");
        System.out.println("=================================");
        System.out.println();
    }

    // ====== 第二階段新增: 實作死亡和戰鬥相關抽象方法 ======
/**
 * 劍士的死亡效果
 * 劍士倒下時，劍會掉落在地上。
 */
    @Override
    public void onDeath() {
        System.out.println("💀" + this.getName() + "倒下了...");
    }
}
