/**
 * Healable - 可治療介面
 *
 * 為什麼需要介面？
 * 1. 治療能力不限於特定類型的角色
 * 2. 魔法師、牧師、聖騎士都可能有治療能力
 * 3. 使用介面可以讓不同類別共享治療能力
 *
 * 誰需要實作這個介面？
 * - Magician（魔法師）：魔法治療
 * - Priest（牧師）：神聖治療
 * - 未來的 Paladin（聖騎士）：聖光治療
 *
 * 介面的好處：
 * - 靈活性：任何類別都可以實作
 * - 多重實作：一個類別可以同時實作 Healable 和 Defendable
 * - 清晰的契約：明確定義「可治療」的行為
 */
public interface Healable {

    /**
     * 治療目標角色（抽象方法）
     * @param target 被治療的角色
     */
    void heal(Role target);

    /**
     * 取得治療力（抽象方法）
     * @return 治療力數值
     */
    int getHealPower();

    /**
     * 檢查是否可以治療（預設方法）
     * 子類別可以選擇性覆寫
     *
     * @return true 如果可以治療
     */
    default boolean canHeal() {
        return getHealPower() > 0;
    }

    /**
     * 顯示治療資訊（預設方法）
     * 提供一個基本的治療資訊顯示
     * 子類別可以選擇性覆寫
     */
    default void showHealInfo() {
        System.out.println("💚 治療力：" + getHealPower() + " 點");
    }
}
