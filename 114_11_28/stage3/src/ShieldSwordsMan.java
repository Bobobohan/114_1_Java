public class ShieldSwordsMan extends SwordsMan {
    private int defenseCapacity;

    // ========== 建構子變更 ==========
    /**
     * 建構子：初始化持盾劍士
     * 注意：現在需要傳入 armor 參數
     */
    public ShieldSwordsMan(String name, int health, int attackPower, int armor, int defenseCapacity) {  // ← 新增 armor 參數
        super(name, health, attackPower, armor);  // ← 傳遞給 SwordsMan
        this.defenseCapacity = defenseCapacity;
    }

    public int getDefenseCapacity() {
        return defenseCapacity;
    }

    @Override
    public void attack(Role opponent) {
        int reducedDamage = this.getAttackPower() - 5;
        System.out.println("🛡️⚔️  " + this.getName() + " 單手揮動 " + getWeaponType() +
                " 攻擊 " + opponent.getName() + "！");
        opponent.takeDamage(reducedDamage);
    }

    public void defence() {
        int oldHealth = this.getHealth();
        this.setHealth(this.getHealth() + defenseCapacity);
        System.out.println("🛡️  " + this.getName() + " 舉起盾牌防禦！恢復 " + defenseCapacity +
                " 點生命值。(" + oldHealth + " → " + this.getHealth() + ")");
    }

    @Override
    public void showSpecialSkill() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║ " + this.getName() + " 的特殊技能      ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ 技能名稱：盾牌猛擊          ║");
        System.out.println("║ 技能描述：使用盾牌撞擊敵人  ║");
        System.out.println("║ 技能效果：造成傷害並暈眩    ║");
        System.out.println("║ 防禦力：+" + defenseCapacity + " 點              ║");
        System.out.println("║ 護甲值：+" + getArmor() + " 點              ║");  // ← 新增護甲顯示
        System.out.println("╚═════════════════════════════╝");
    }

    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 力竭倒下...");
        System.out.println("🛡️  厚重的盾牌砸在地上，揚起一陣塵土。");
        System.out.println("⚔️  " + getWeaponType() + " 也隨之掉落。");  // ← 使用 getWeaponType()
        System.out.println("---");
    }

    // ========== 覆寫 MeleeRole 的方法 ==========

    /**
     * 取得武器類型（覆寫）
     * 持盾劍士使用單手劍+盾牌
     */
    @Override
    public String getWeaponType() {
        return "單手劍+盾牌";
    }

    /**
     * 近戰特殊準備（覆寫）
     * 持盾劍士會檢查盾牌
     */
    @Override
    protected void onMeleePrepare() {
        System.out.println("🛡️  檢查盾牌的牢固程度，準備防禦姿態...");
        System.out.println("⚔️  同時確認 " + getWeaponType() + " 的配合度。");
    }

    /**
     * 戰後行為（覆寫）
     * 持盾劍士會修補盾牌
     */
    @Override
    public void afterBattle() {
        System.out.println("🛡️  " + this.getName() + " 檢視盾牌上的新傷痕，並進行簡單修補。");
    }
}
