package com.parking;

/**
 * Member - 會員類別
 * 封裝會員基本資訊、儲值金與身障資格。
 */
public class Member {
    // ========== 屬性 (完全貼合規格書) ==========
    private String memberId;           // 會員 ID (不可為空或 null)
    private String name;               // 姓名 (不可為空或 null)
    private double balance = 0;        // 儲值金餘額 (預設 0，不可為負)
    private boolean hasMonthlyPass;    // 是否持有月票
    private String disabilityCardId;   // 身心障礙手冊編號
    private boolean isHandicapped;     // 是否具備身障資格

    // ========== 建構子 ==========
    public Member(String memberId, String name) {
        // 驗證基本資訊
        if (memberId == null || memberId.trim().isEmpty() ||
                name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("不可為空或 null");
        }
        this.memberId = memberId;
        this.name = name;
    }

    // ========== 行為方法 ==========

    /**
     * 執行扣款
     * @param amount 扣款金額
     * @throws IllegalStateException 餘額不足時拋出指定訊息
     */
    public void deductBalance(double amount) {
        if (balance < amount) {
            // 規格書中提到的訊息
            throw new IllegalStateException("會員帳戶餘額不足，請先加值");
        }
        balance -= amount;
    }

    // ========== Getter 與 Setter (依規格書實作) ==========

    public String getMemberId() { return memberId; }

    public String getName() { return name; }

    public double getBalance() { return balance; }

    // 設定餘額時檢查不可為負數
    public void setBalance(double balance) {
        if (balance < 0) throw new IllegalArgumentException("餘額不可為負數");
        this.balance = balance;
    }

    public boolean isHasMonthlyPass() { return hasMonthlyPass; }
    public void setHasMonthlyPass(boolean hasMonthlyPass) { this.hasMonthlyPass = hasMonthlyPass; }

    public String getDisabilityCardId() { return disabilityCardId; }
    public void setDisabilityCardId(String disabilityCardId) { this.disabilityCardId = disabilityCardId; }

    public boolean isHandicapped() { return isHandicapped; }
    public void setHandicapped(boolean handicapped) { this.isHandicapped = handicapped; }
}
