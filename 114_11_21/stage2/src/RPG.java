public class RPG {
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════");
        System.out.println("        🎮 RPG 遊戲 - 第二階段");
        System.out.println("   展示：具體方法 + 抽象方法的結合");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        // 建立角色
        SwordsMan swordsMan_light = new SwordsMan("光明劍士", 100, 20);
        SwordsMan swordsMan_dark = new SwordsMan("黑暗劍士", 100, 25);
        Magician magician_light = new Magician("光明法師", 80, 15, 10);
        Magician magician_dark = new Magician("黑暗法師", 80, 20, 5);
        ShieldSwordsMan shieldSwordsMan = new ShieldSwordsMan("持盾劍士", 120, 18, 8);

        Role[] gameRoles = {swordsMan_light, swordsMan_dark, magician_light, magician_dark, shieldSwordsMan};

        // ========== 展示所有角色的特殊技能 ==========
        System.out.println("════════════════════════════════════════");
        System.out.println("          📋 角色特殊技能展示");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        for (Role role : gameRoles) {
            role.showSpecialSkill();
            System.out.println();
        }

        System.out.println("════════════════════════════════════════");
        System.out.println();

        // ========== 第二階段新增：完整的戰鬥流程 ==========
        System.out.println("⚔️  戰鬥開始！");
        System.out.println();

        int round = 1;
        for (Role currentRole : gameRoles) {
            if (!currentRole.isAlive()) {
                continue; // 跳過已經死亡的角色
            }

            System.out.println("━━━━━━━━━━ 第 " + round + " 回合 ━━━━━━━━━━");

            // 戰前準備（抽象方法）
            currentRole.prepareBattle();
            System.out.println();

            // 執行動作
            if (currentRole instanceof SwordsMan && !(currentRole instanceof ShieldSwordsMan)) {
                // 一般劍士的行為
                Role target = getRandomAliveTarget(gameRoles, currentRole);
                if (target != null) {
                    currentRole.attack(target);
                }
            } else if (currentRole instanceof ShieldSwordsMan) {
                // 持盾劍士的行為：有機會先防禦
                if (Math.random() < 0.3) {
                    ((ShieldSwordsMan) currentRole).defence();
                    System.out.println();
                }
                Role target = getRandomAliveTarget(gameRoles, currentRole);
                if (target != null) {
                    currentRole.attack(target);
                }
            } else if (currentRole instanceof Magician) {
                // 魔法師的行為：攻擊或治療
                Magician magician = (Magician) currentRole;
                if (Math.random() < 0.6) {
                    // 60% 機率攻擊
                    Role target = getRandomAliveTarget(gameRoles, currentRole);
                    if (target != null) {
                        currentRole.attack(target);
                    }
                } else {
                    // 40% 機率治療
                    Role ally = getRandomAliveRole(gameRoles);
                    if (ally != null) {
                        magician.heal(ally);
                    }
                }
            }

            System.out.println();

            // 戰後行為（抽象方法）
            if (currentRole.isAlive()) {
                currentRole.afterBattle();
            }

            System.out.println();
            round++;
        }

        // ========== 戰鬥結束，顯示存活者 ==========
        System.out.println("════════════════════════════════════════");
        System.out.println("          🏆 戰鬥結束");
        System.out.println("════════════════════════════════════════");
        System.out.println();
        System.out.println("存活的角色：");
        for (Role role : gameRoles) {
            if (role.isAlive()) {
                System.out.println("✅ " + role.getName() + " - 生命值：" + role.getHealth());
            }
        }

        System.out.println();
        System.out.println("陣亡的角色：");
        for (Role role : gameRoles) {
            if (!role.isAlive()) {
                System.out.println("💀 " + role.getName());
            }
        }
    }

    /**
     * 隨機選擇一個存活的目標（排除自己）
     */
    private static Role getRandomAliveTarget(Role[] roles, Role self) {
        Role[] aliveRoles = new Role[roles.length];
        int count = 0;

        for (Role role : roles) {
            if (role != self && role.isAlive()) {
                aliveRoles[count++] = role;
            }
        }

        if (count == 0) return null;
        return aliveRoles[(int) (Math.random() * count)];
    }

    /**
     * 隨機選擇一個存活的角色（包括自己）
     */
    private static Role getRandomAliveRole(Role[] roles) {
        Role[] aliveRoles = new Role[roles.length];
        int count = 0;

        for (Role role : roles) {
            if (role.isAlive()) {
                aliveRoles[count++] = role;
            }
        }

        if (count == 0) return null;
        return aliveRoles[(int) (Math.random() * count)];
    }
}
