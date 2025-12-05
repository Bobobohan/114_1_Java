package com.rpg;

import com.rpg.core.Role;
import com.rpg.interfaces.*;
import com.rpg.roles.melee.*;
import com.rpg.roles.ranged.*;
public class RPG {
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════");
        System.out.println("        🎮 RPG 遊戲 - 第四階段");
        System.out.println("          展示：介面的應用");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        System.out.println("📋 類別與介面結構：");
        System.out.println("Role (抽象類別)");
        System.out.println("├─ MeleeRole");
        System.out.println("│  ├─ SwordsMan");
        System.out.println("│  ├─ ShieldSwordsMan (實作 Defendable)");
        System.out.println("│  └─ Paladin (實作 Defendable + Healable) ⭐");
        System.out.println("└─ RangedRole");
        System.out.println("   ├─ Magician (實作 Healable)");
        System.out.println("   └─ Archer");
        System.out.println();
        System.out.println("介面 (Interface)：");
        System.out.println("├─ Defendable：防禦能力");
        System.out.println("└─ Healable：治療能力");
        System.out.println();
        System.out.println("════════════════════════════════════════");
        System.out.println();

        // 建立角色
        SwordsMan swordsMan = new SwordsMan("光明劍士", 100, 20, 5);
        ShieldSwordsMan shieldSwordsMan = new ShieldSwordsMan("持盾劍士", 120, 18, 8, 10);
        Magician magician = new Magician("光明法師", 80, 15, 10, 8, 100);
        Archer archer = new Archer("精靈射手", 90, 18, 10, 80, 30);
        Paladin paladin = new Paladin("聖騎士", 110, 17, 6, 12, 12, 100);

        Role[] gameRoles = {swordsMan, shieldSwordsMan, magician, archer, paladin};

        // ========== 展示介面能力 ==========
        System.out.println("════════════════════════════════════════");
        System.out.println("          🔍 介面能力展示");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        System.out.println("【可防禦角色 (Defendable)】");
        for (Role role : gameRoles) {
            if (role instanceof Defendable) {
                Defendable defender = (Defendable) role;
                System.out.println("✅ " + role.getName() +
                        " - 防禦力：" + defender.getDefenseCapacity() +
                        " (可防禦：" + defender.canDefend() + ")");
            }
        }
        System.out.println();

        System.out.println("【可治療角色 (Healable)】");
        for (Role role : gameRoles) {
            if (role instanceof Healable) {
                Healable healer = (Healable) role;
                System.out.println("✅ " + role.getName() +
                        " - 治療力：" + healer.getHealPower() +
                        " (可治療：" + healer.canHeal() + ")");
            }
        }
        System.out.println();

        System.out.println("【多重能力角色】");
        for (Role role : gameRoles) {
            if (role instanceof Defendable && role instanceof Healable) {
                System.out.println("⭐ " + role.getName() +
                        " - 同時擁有防禦和治療能力！");
            }
        }
        System.out.println();
        System.out.println("════════════════════════════════════════");
        System.out.println();

        // ========== 展示技能 ==========
        System.out.println("════════════════════════════════════════");
        System.out.println("          📋 角色技能展示");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        for (Role role : gameRoles) {
            role.showSpecialSkill();
            System.out.println();
        }

        System.out.println("════════════════════════════════════════");
        System.out.println();

        // ========== 戰鬥模擬 ==========
        System.out.println("⚔️  戰鬥開始！");
        System.out.println();

        int round = 1;
        int maxRounds = 6;

        for (Role currentRole : gameRoles) {
            if (round > maxRounds) break;
            if (!currentRole.isAlive()) continue;

            System.out.println("━━━━━━━━━━ 第 " + round + " 回合 ━━━━━━━━━━");

            // 戰前準備
            currentRole.prepareBattle();
            System.out.println();

            // 執行動作
            if (currentRole instanceof Paladin) {
                // 聖騎士：展示多重能力
                Paladin p = (Paladin) currentRole;
                double action = Math.random();

                if (action < 0.3) {
                    // 30% 防禦
                    p.defend();
                } else if (action < 0.6) {
                    // 30% 治療
                    Role ally = getRandomAliveRole(gameRoles);
                    if (ally != null) {
                        p.heal(ally);
                    }
                } else {
                    // 40% 攻擊
                    Role target = getRandomAliveTarget(gameRoles, currentRole);
                    if (target != null) {
                        currentRole.attack(target);
                    }
                }
            } else if (currentRole instanceof ShieldSwordsMan) {
                // 持盾劍士：可能防禦
                ShieldSwordsMan shield = (ShieldSwordsMan) currentRole;
                if (Math.random() < 0.3) {
                    shield.defend();
                    System.out.println();
                }
                Role target = getRandomAliveTarget(gameRoles, currentRole);
                if (target != null) {
                    currentRole.attack(target);
                }
            } else if (currentRole instanceof Magician) {
                // 魔法師：攻擊或治療
                Magician mage = (Magician) currentRole;
                if (Math.random() < 0.6) {
                    Role target = getRandomAliveTarget(gameRoles, currentRole);
                    if (target != null) {
                        currentRole.attack(target);
                    }
                } else {
                    Role ally = getRandomAliveRole(gameRoles);
                    if (ally != null) {
                        mage.heal(ally);
                    }
                }
            } else {
                // 其他角色：直接攻擊
                Role target = getRandomAliveTarget(gameRoles, currentRole);
                if (target != null) {
                    currentRole.attack(target);
                }
            }

            System.out.println();

            // 戰後行為
            if (currentRole.isAlive()) {
                currentRole.afterBattle();
            }

            System.out.println();
            round++;
        }

        // ========== 戰鬥結束 ==========
        System.out.println("════════════════════════════════════════");
        System.out.println("          🏆 戰鬥結束");
        System.out.println("════════════════════════════════════════");
        System.out.println();

        System.out.println("【最終狀態】");
        for (Role role : gameRoles) {
            if (role.isAlive()) {
                String abilities = "";
                if (role instanceof Defendable && role instanceof Healable) {
                    abilities = " [防禦+治療]";
                } else if (role instanceof Defendable) {
                    abilities = " [防禦]";
                } else if (role instanceof Healable) {
                    abilities = " [治療]";
                }

                System.out.println("✅ " + role.getName() + abilities +
                        " - 生命值：" + role.getHealth());
            } else {
                System.out.println("💀 " + role.getName() + " - 已陣亡");
            }
        }

        System.out.println();
        System.out.println("════════════════════════════════════════");
        System.out.println("      感謝遊玩！介面展示完成！");
        System.out.println("════════════════════════════════════════");
    }

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

