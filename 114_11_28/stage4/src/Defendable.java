/**
 * Defendable - 可防禦介面
 *
 * 為什麼需要介面？
 * 1. Java 只支援單一繼承，但可以實作多個介面
 * 2. 介面描述「能做什麼」（can-do），抽象類別描述「是什麼」（is-a）
 * 3. 不同類型的角色都可能有防禦能力
 *
 * 誰需要實作這個介面？
 * - ShieldSwordsMan（持盾劍士）：用盾牌防禦
 * - 未來的角色如 Paladin（聖騎士）：用聖光防禦
 *
 * 介面 vs 抽象類別：
 * - 介面：定義「能力」，可以實作多個
 * - 抽象類別：定義「本質」，只能繼承一個
 */
public interface Defendable {

    /**
     * 執行防禦動作（抽象方法）
     * 實作這個介面的類別必須提供防禦的具體實作
     */
    void defend();

    /**
     * 取得防禦力（抽象方法）
     * @return 防禦力數值
     */
    int getDefenseCapacity();

    /**
     * 檢查是否可以防禦（預設方法）
     * 這是介面的預設方法（default method，Java 8+）
     * 子類別可以選擇性覆寫
     *
     * @return true 如果可以防禦
     */
    default boolean canDefend() {
        return getDefenseCapacity() > 0;
    }
}
