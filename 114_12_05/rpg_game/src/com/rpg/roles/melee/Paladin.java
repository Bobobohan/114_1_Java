/**
 * Paladin - 聖騎士類別
 *
 * 第四階段新增：展示多重介面實作的強大之處
 *
 * 設計說明：
 * - 繼承：MeleeRole（近戰角色）
 * - 實作：Defendable + Healable（同時實作兩個介面！）
 *
 * 這是本階段的核心範例：
 * 1. 展示一個類別可以實作多個介面
 * 2. 聖騎士既能防禦又能治療
 * 3. 解決了單一繼承的限制
 *
 * 如果沒有介面：
 * - 無法同時繼承「防禦類別」和「治療類別」
 * - 必須在某個父類別中重複實作
 *
 * 使用介面的好處：
 * - 聖騎士可以同時擁有防禦和治療能力
 * - 程式碼清晰，職責明確
 * - 符合「組合優於繼承」的原則
 */
package com.rpg.roles.melee;

import com.rpg.core.Role;
import com.rpg.interfaces.Defendable;
import com.rpg.interfaces.Healable;

public class Paladin extends MeleeRole implements Defendable, Healable {  // ← 同時實作兩個介面
    private int defenseCapacity;  // 防禦力
    private int healPower;        // 治療力
    private int holyPower;        // 聖能值（特有資源）
    private int maxHolyPower;

    /**
     * 建構子：初始化聖騎士
     */
    public Paladin(String name, int health, int attackPower, int armor,
                   int defenseCapacity, int healPower, int maxHolyPower) {
        super(name, health, attackPower, armor);
        this.defenseCapacity = defenseCapacity;
        this.healPower = healPower;
        this.maxHolyPower = maxHolyPower;
        this.holyPower = maxHolyPower;
    }

    // ========== 聖能管理方法 ==========

    public int getHolyPower() {
        return holyPower;
    }

    private boolean consumeHolyPower(int amount) {
        if (holyPower >= amount) {
            holyPower -= amount;
            System.out.println("✨ 消耗 " + amount + " 點聖能，剩餘：" + holyPower + "/" + maxHolyPower);
            return true;
        } else {
            System.out.println("❌ 聖能不足！需要 " + amount + "，目前只有 " + holyPower);
            return false;
        }
    }

    private void restoreHolyPower(int amount) {
        int oldPower = holyPower;
        holyPower = Math.min(holyPower + amount, maxHolyPower);
        System.out.println("🌟 恢復 " + (holyPower - oldPower) + " 點聖能 (" +
                oldPower + " → " + holyPower + ")");
    }

    // ========== 覆寫 Role 的方法 ==========

    @Override
    public void attack(Role opponent) {
        System.out.println("⚔️✨ " + this.getName() + " 揮動 " + getWeaponType() +
                " 進行神聖攻擊 " + opponent.getName() + "！");
        System.out.println("🌟 聖光附著在武器上，增強傷害！");
        opponent.takeDamage(this.getAttackPower());

        // 攻擊時恢復少量聖能
        restoreHolyPower(5);
    }

    @Override
    public void showSpecialSkill() {
        System.out.println("╔═════════════════════════════╗");
        System.out.println("║ " + this.getName() + " 的特殊技能      ║");
        System.out.println("╠═════════════════════════════╣");
        System.out.println("║ 技能名稱：神聖審判          ║");
        System.out.println("║ 技能描述：召喚聖光審判敵人  ║");
        System.out.println("║ 攻擊效果：神聖傷害          ║");
        System.out.println("║ 防禦力：+" + defenseCapacity + " 點              ║");
        System.out.println("║ 治療力：+" + healPower + " 點              ║");
        System.out.println("║ 聖能值：" + holyPower + "/" + maxHolyPower + "            ║");
        System.out.println("╚═════════════════════════════╝");
    }

    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 完成了神聖的使命...");
        System.out.println("✨ 聖光環繞身體，緩緩消散。");
        System.out.println("⚔️  " + getWeaponType() + " 插在地上，散發著微弱的光芒。");
        System.out.println("🛡️  盾牌上的聖徽依然閃耀。");
        System.out.println("---");
    }

    @Override
    public String getWeaponType() {
        return "聖劍+聖盾";
    }

    @Override
    protected void onMeleePrepare() {
        System.out.println("🙏 " + this.getName() + " 低聲祈禱，聖光開始聚集...");
        System.out.println("✨ 聖劍和聖盾都散發出神聖的光芒。");
        System.out.println("📊 聖能值：" + holyPower + "/" + maxHolyPower);
    }

    @Override
    public void afterBattle() {
        System.out.println("🙏 " + this.getName() + " 感謝聖光的庇護。");
        restoreHolyPower(10);
    }

    // ========== 實作 Defendable 介面 ==========

    /**
     * 實作防禦能力
     * 聖騎士使用聖盾防禦，需要消耗聖能
     */
    @Override
    public void defend() {
        if (!consumeHolyPower(10)) {
            System.out.println("❌ 無法使用聖盾防禦！");
            return;
        }

        int oldHealth = this.getHealth();
        this.setHealth(this.getHealth() + defenseCapacity);
        System.out.println("🛡️✨ " + this.getName() + " 舉起聖盾，聖光形成防護罩！");
        System.out.println("💚 恢復 " + defenseCapacity + " 點生命值 (" +
                oldHealth + " → " + this.getHealth() + ")");
    }

    @Override
    public int getDefenseCapacity() {
        return defenseCapacity;
    }

    // ========== 實作 Healable 介面 ==========

    /**
     * 實作治療能力
     * 聖騎士使用聖光治療，需要消耗聖能
     */
    @Override
    public void heal(Role target) {
        if (!consumeHolyPower(15)) {
            System.out.println("❌ 無法施放聖光治療！");
            return;
        }

        int oldHealth = target.getHealth();
        target.setHealth(target.getHealth() + this.healPower);
        System.out.println("💚✨ " + this.getName() + " 施放聖光治療 " + target.getName());
        System.out.println("🌟 神聖的光芒包圍著 " + target.getName());
        System.out.println("💚 恢復 " + healPower + " 點生命值 (" +
                oldHealth + " → " + target.getHealth() + ")");
    }

    @Override
    public int getHealPower() {
        return healPower;
    }

    /**
     * 覆寫介面的預設方法
     * 聖騎士的治療還需要檢查聖能
     */
    @Override
    public boolean canHeal() {
        return getHealPower() > 0 && holyPower >= 15;
    }

    @Override
    public String toString() {
        return super.toString() + ", 防禦力: " + defenseCapacity +
                ", 治療力: " + healPower + ", 聖能: " + holyPower + "/" + maxHolyPower;
    }
}
