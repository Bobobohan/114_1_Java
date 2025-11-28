public class Magician extends RangedRole {  // ← 改為繼承 RangedRole
    private int healPower;

    // ========== 建構子變更 ==========
    /**
     * 建構子：初始化魔法師
     * 注意：現在需要傳入 range 和 maxEnergy 參數
     */
    public Magician(String name, int health, int attackPower, int healPower,
                    int range, int maxEnergy) {  // ← 新增 range 和 maxEnergy 參數
        super(name, health, attackPower, range, maxEnergy);  // ← 呼叫 RangedRole 建構子
        this.healPower = healPower;
    }

    public int getHealPower() {
        return healPower;
    }

    // ========== 方法修改：加入能量消耗 ==========

    @Override
    public void attack(Role opponent) {
        // ← 新增：檢查能量是否足夠
        if (!consumeEnergy(15)) {
            System.out.println("❌ " + getName() + " 能量不足，無法施放魔法！");
            return;
        }

        System.out.println("✨ " + getName() + " 施放 " + getRangedAttackType() +
                " 攻擊 " + opponent.getName() + "！");
        opponent.takeDamage(this.getAttackPower());
    }

    public void heal(Role ally) {
        // ← 新增：檢查能量是否足夠
        if (!consumeEnergy(10)) {
            System.out.println("❌ " + getName() + " 能量不足，無法施放治療！");
            return;
        }

        int oldHealth = ally.getHealth();
        ally.setHealth(ally.getHealth() + this.healPower);
        System.out.println("💚 " + this.getName() + " 治療 " + ally.getName() +
                " 回復 " + healPower + " 點生命值 (" +
                oldHealth + " → " + ally.getHealth() + ")");
    }

    @Override
    public void showSpecialSkill() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║ " + this.getName() + " 的特殊技能        ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ 技能名稱：元素爆發          ║");
        System.out.println("║ 技能描述：召喚強大魔法攻擊  ║");
        System.out.println("║ 技能效果：範圍魔法傷害      ║");
        System.out.println("║ 額外效果：恢復自身魔力      ║");
        System.out.println("║ 射程：" + getRange() + " 米                ║");  // ← 新增射程顯示
        System.out.println("╚═════════════════════════════╝");
    }

    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 的生命之火熄滅了...");
        System.out.println("✨ " + this.getName() + " 的身體化為無數魔法粒子，消散在空氣中。");
        System.out.println("🌟 魔法書掉落在地上，微微發光。");
        System.out.println("---");
    }

    // ========== 新增方法：實作 RangedRole 的抽象方法 ==========

    /**
     * 取得遠程攻擊類型（實作 RangedRole 的抽象方法）
     * 魔法師使用魔法彈
     */
    @Override
    public String getRangedAttackType() {
        return "魔法彈";
    }

    /**
     * 遠程特殊準備（實作 RangedRole 的抽象方法）
     * 魔法師會吟唱咒語
     */
    @Override
    protected void onRangedPrepare() {
        System.out.println("📖 翻開魔法書，開始吟唱古老的咒語...");
        System.out.println("✨ 魔法能量在周圍凝聚，空氣中閃爍著神秘的光芒。");
    }

    /**
     * 遠程特殊恢復（實作 RangedRole 的抽象方法）
     * 魔法師會冥想
     */
    @Override
    protected void onRangedRecover() {
        System.out.println("🧘 " + this.getName() + " 閉目冥想，深度恢復魔力。");
    }

    @Override
    public String toString() {
        return super.toString() + ", 治癒力: " + healPower;
    }
}
