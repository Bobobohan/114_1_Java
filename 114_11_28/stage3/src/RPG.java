public class RPG {
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════");
        System.out.println("        🎮 RPG 遊戲 - 第三階段");
        System.out.println("      展示：多層繼承結構設計");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        // ========== 顯示類別繼承結構 ==========
        System.out.println("📋 類別繼承結構：");
        System.out.println("Role (最高層)");
        System.out.println("├─ MeleeRole (近戰角色)");
        System.out.println("│  ├─ SwordsMan (劍士)");
        System.out.println("│  └─ ShieldSwordsMan (持盾劍士)");
        System.out.println("└─ RangedRole (遠程角色)");
        System.out.println("   ├─ Magician (魔法師)");
        System.out.println("   └─ Archer (弓箭手)");
        System.out.println();

        // ========== 建立角色（參數變更） ==========

        // 近戰角色：需要 armor（護甲值）
        SwordsMan swordsMan_light = new SwordsMan("光明劍士", 100, 20, 5);  // ← 新增 armor
        SwordsMan swordsMan_dark = new SwordsMan("黑暗劍士", 100, 25, 3);   // ← 新增 armor
        ShieldSwordsMan shieldSwordsMan = new ShieldSwordsMan("持盾劍士", 120, 18, 8, 10);  // ← 新增 armor

        // 遠程角色：需要 range（射程）和 maxEnergy（能量值）
        Magician magician_light = new Magician("光明法師", 80, 15, 10, 8, 100);  // ← 新增 range, maxEnergy
        Magician magician_dark = new Magician("黑暗法師", 80, 20, 5, 8, 100);    // ← 新增 range, maxEnergy
        Archer archer = new Archer("精靈射手", 90, 18, 10, 80, 30);  // ← 新增角色

        Role[] gameRoles = {swordsMan_light, swordsMan_dark, shieldSwordsMan,
                magician_light, magician_dark, archer};

        // ========== 展示類別特性（新增） ==========
        System.out.println("════════════════════════════════════════");
        System.out.println("          🔍 角色類別特性展示");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        System.out.println("【近戰角色特性】");
        for (Role role : gameRoles) {
            if (role instanceof MeleeRole) {
                MeleeRole melee = (MeleeRole) role;
                System.out.println(role.getName() + "：武器=" + melee.getWeaponType() +
                        "，護甲=" + melee.getArmor());
            }
        }
        System.out.println();

        System.out.println("【遠程角色特性】");
        for (Role role : gameRoles) {
            if (role instanceof RangedRole) {
                RangedRole ranged = (RangedRole) role;
                System.out.println(role.getName() + "：攻擊類型=" + ranged.getRangedAttackType() +
                        "，射程=" + ranged.getRange() +
                        "，能量=" + ranged.getEnergy() + "/" + ranged.getMaxEnergy());
            }
        }
        System.out.println();

        // 戰鬥流程...
    }
}
