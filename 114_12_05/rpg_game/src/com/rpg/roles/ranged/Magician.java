package com.rpg.roles.ranged;

import com.rpg.core.Role;
import com.rpg.interfaces.Healable;
public class Magician extends RangedRole implements Healable {  // ← 實作 Healable 介面
    private int healPower;

    public Magician(String name, int health, int attackPower, int healPower,
                    int range, int maxEnergy) {
        super(name, health, attackPower, range, maxEnergy);
        this.healPower = healPower;
    }

    @Override
    public void attack(Role opponent) {
        if (!consumeEnergy(15)) {
            System.out.println("❌ " + getName() + " 能量不足，無法施放魔法！");
            return;
        }

        System.out.println("✨ " + getName() + " 施放 " + getRangedAttackType() +
                " 攻擊 " + opponent.getName() + "！");
        opponent.takeDamage(this.getAttackPower());
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
        System.out.println("║ 射程：" + getRange() + " 米                ║");
        System.out.println("║ 治療力：" + healPower + " 點            ║");  // ← 新增治療力顯示
        System.out.println("╚═════════════════════════════╝");
    }

    @Override
    public void onDeath() {
        System.out.println("💀 " + this.getName() + " 的生命之火熄滅了...");
        System.out.println("✨ " + this.getName() + " 的身體化為無數魔法粒子，消散在空氣中。");
        System.out.println("🌟 魔法書掉落在地上，微微發光。");
        System.out.println("---");
    }

    @Override
    public String getRangedAttackType() {
        return "魔法彈";
    }

    @Override
    protected void onRangedPrepare() {
        System.out.println("📖 翻開魔法書，開始吟唱古老的咒語...");
        System.out.println("✨ 魔法能量在周圍凝聚，空氣中閃爍著神秘的光芒。");
    }

    @Override
    protected void onRangedRecover() {
        System.out.println("🧘 " + this.getName() + " 閉目冥想，深度恢復魔力。");
    }

    // ========== 第四階段新增：實作 Healable 介面 ==========

    /**
     * 實作 Healable 介面的 heal() 方法
     * 魔法師使用魔法能量進行治療
     */
    @Override  // ← 加上註解，標示實作介面方法
    public void heal(Role target) {
        // 檢查能量是否足夠
        if (!consumeEnergy(10)) {
            System.out.println("❌ " + getName() + " 能量不足，無法施放治療！");
            return;
        }

        int oldHealth = target.getHealth();
        target.setHealth(target.getHealth() + this.healPower);
        System.out.println("💚 " + this.getName() + " 施放治療魔法，治療 " + target.getName());
        System.out.println("✨ 恢復 " + healPower + " 點生命值 (" +
                oldHealth + " → " + target.getHealth() + ")");
    }

    /**
     * 實作 Healable 介面的 getHealPower() 方法
     */
    @Override  // ← 加上註解
    public int getHealPower() {
        return healPower;
    }

    // 注意：canHeal() 和 showHealInfo() 使用介面的預設實作

    @Override
    public String toString() {
        return super.toString() + ", 治癒力: " + healPower;
    }
}