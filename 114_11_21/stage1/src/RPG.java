public class RPG {

    public static void main(String[] args) {

        // 建立角色物件
        SwordsMan swordsMan_light = new SwordsMan("光劍劍士", 100, 20);
        SwordsMan swordsMan_dark = new SwordsMan("影劍劍士", 100, 25);

        Magician magician_light = new Magician("光明法師", 80, 15, 10);
        Magician magician_dark = new Magician("暗黑法師", 80, 20, 5);

        ShieldSwordsMan shieldSwordsMan = new ShieldSwordsMan("盾牌劍士", 120, 18, 0);

        // 建立角色陣列
        Role[] gameRoles = { swordsMan_light, swordsMan_dark, magician_light, magician_dark, shieldSwordsMan };

        // 戰鬥過程
        System.out.println("\n戰鬥開始!");

        for (Role currentRole : gameRoles) {

            if (!currentRole.isAlive()) {
                continue; // 死亡角色跳過
            }

            if (currentRole instanceof SwordsMan) {

                Role target = gameRoles[(int)(Math.random() * gameRoles.length)];

                if (target instanceof ShieldSwordsMan)
                    ((ShieldSwordsMan) target).defence();

                currentRole.attack(target);

            } else if (currentRole instanceof Magician) {

                Magician magician = (Magician) currentRole;

                if (Math.random() < 0.5) {
                    Role target = gameRoles[(int)(Math.random() * gameRoles.length)];

                    if (target instanceof ShieldSwordsMan)
                        ((ShieldSwordsMan) target).defence();

                    currentRole.attack(target);
                } else {
                    magician.heal(gameRoles[(int)(Math.random() * gameRoles.length)]);
                }
            }
        }
    }
}


