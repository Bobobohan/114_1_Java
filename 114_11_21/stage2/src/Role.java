public abstract class Role {

    // 角色名稱
    private String name;

    // 生命值
    private int health;

    // 攻擊力
    private int attackPower;

    // 建構子：初始化角色的名稱、生命值和攻擊力
    public Role(String name, int health, int attackPower) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
    }

    // 取得角色名稱
    public String getName() { return name; }

    // 取得生命值
    public int getHealth() { return health; }

    // 取得攻擊力
    public int getAttackPower() { return attackPower; }

    // 設定生命值
    public void setHealth(int health) { this.health = health; }

    // 檢查角色是否存活
    public boolean isAlive() { return health > 0; }

    public abstract void attack(Role opponent);

    @Override
    public String toString() {
        return "角色名稱: " + name + ", 生命值: " + health;
    }
}
