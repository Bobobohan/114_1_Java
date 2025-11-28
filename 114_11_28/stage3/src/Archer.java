/**
 * Archer - 弓箭手類別
 *
 * 第三階段新增角色：
 * - 繼承 RangedRole，展示遠程角色的另一種實作
 * - 與魔法師同為遠程角色，但攻擊方式不同
 * - 使用箭矢而非魔法
 */
public class Archer extends RangedRole {
    // ========== 特有屬性 ==========
    private int arrowCount;    // 箭矢數量
    private int maxArrows;     // 最大箭矢數

    // ========== 建構子 ==========
    /**
     * 建構子：初始化弓箭手
     */
    public Archer(String name, int health, int attackPower,
                  int range, int maxEnergy, int maxArrows) {
        super(name, health, attackPower, range, maxEnergy);
        this.maxArrows = maxArrows;
        this.arrowCount = maxArrows;
    }

    // ========== 箭矢管理方法 ==========

    /**
     * 取得箭矢數量
     */
    public int getArrowCount() {
        return arrowCount;
    }

    /**
     * 補充箭矢
     */
    public void reloadArrows(int amount) {
        int oldCount = arrowCount;
        arrowCount = Math.min(arrowCount + amount, maxArrows);
        System.out.println("🏹 補充箭矢 " + (arrowCount - oldCount) +
                " 支 (" + oldCount + " → " + arrowCount + ")");
    }

    // ========== 覆寫方法 ==========

    /**
     * 攻擊對手
     * 需要消耗箭矢和能量（拉弓需要體力）
     */
    @Override
    public void attack(Role opponent) {
        // 檢查箭矢是否足夠
        if (arrowCount <= 0) {
            System.out.println("❌ " + getName() + " 箭矢用盡，無法攻擊！");
            return;
        }

        // 檢查能量是否足夠（拉弓需要體力）
        if (!consumeEnergy(10)) {
            System.out.println("❌ " + getName() + " 體力不足，無法拉弓！");
            return;
        }

        arrowCount--;
        System.out.println("🏹 " + getName() + " 射出 " + getRangedAttackType() +
                " 攻擊 " + opponent.getName() + "！");
        System.out.println("📊 剩餘箭矢：" + arrowCount + "/" + maxArrows);
        opponent.takeDamage(this.getAttackPower());
    }

    /**
     * 展示特殊技能
     */
    @Override
    public void showSpecialSkill() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║ " + this.getName() + " 的特殊技能        ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ 技能名稱：多重箭矢          ║");
        System.out.println("║ 技能描述：同時射出三支箭    ║");
        System.out.println("║ 技能效果：分別命中三個目標  ║");
        System.out.println("║ 射程：" + getRange() + " 米                ║");
        System.out.println("║ 箭矢：" + arrowCount + "/" + maxArrows + "               ║");
        System.out.println("╚═════════════════════════════╝");
    }

    /**
     * 弓箭手的死亡效果
     */
    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 倒下了...");
        System.out.println("🏹 弓掉落在地上，弓弦斷裂。");
        System.out.println("🎯 箭囊散落一地，箭矢四散。");
        System.out.println("---");
    }

    // ========== 實作 RangedRole 的抽象方法 ==========

    /**
     * 取得遠程攻擊類型
     * 弓箭手使用箭矢
     */
    @Override
    public String getRangedAttackType() {
        return "精準箭矢";
    }

    /**
     * 遠程特殊準備
     * 弓箭手會檢查弓弦和箭矢
     */
    @Override
    protected void onRangedPrepare() {
        System.out.println("🏹 檢查弓弦的張力和箭矢的狀態...");
        System.out.println("🎯 調整呼吸，進入射擊姿態。");
    }

    /**
     * 遠程特殊恢復
     * 弓箭手會放鬆肌肉並補充箭矢
     */
    @Override
    protected void onRangedRecover() {
        System.out.println("💪 " + this.getName() + " 放鬆手臂肌肉，恢復體力。");
        if (arrowCount < maxArrows) {
            reloadArrows(5);  // 戰後補充 5 支箭
        }
    }

    @Override
    public String toString() {
        return super.toString() + ", 箭矢: " + arrowCount + "/" + maxArrows;
    }
}
