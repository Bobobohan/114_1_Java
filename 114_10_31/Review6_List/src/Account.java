import java.time.LocalDateTime;

public class Account {
    private static int accountCount = 0; // 帳戶數量統計

    // 帳戶號碼，唯一識別每個帳戶
    private String accountNumber;
    // 帳戶擁有者名稱
    private String ownerName;
    private Person owner;
    // 帳戶餘額
    private double balance;
    private  Date openingDate;
    private Time2 openingTime;
    private LocalDateTime now;

    public Account(String accountNumber, String ownerName, double initialBalance, Date openingDate, Time2 openingTime) {
        LocalDateTime now = LocalDateTime.now();
        this.setAccountNumber(accountNumber);
        this.ownerName = ownerName;
        try {
            this.setBalance(initialBalance);
        } catch (IllegalArgumentException e) {
            System.out.println("初始餘額錯誤: " + e.getMessage() + "，將餘額設為0");
        }
        // 紀錄當前日期和時間初始化開戶日期和時間
        this.openingDate = new Date(now.getMonthValue(), now.getDayOfMonth(), now.getYear());
        this.openingTime = new Time2(now.getHour(), now.getMinute(), now.getSecond());
        accountCount++; // 帳戶數量加1
    }


    /**
     * 建構子，初始化帳戶號碼與初始餘額
     * @param accountNumber 帳戶號碼
     * @param initialBalance 初始餘額
     */
    public Account(String accountNumber, String ownerName, double initialBalance) {
        this.setAccountNumber(accountNumber);
        this.ownerName = ownerName;
        try {
            this.setBalance(initialBalance);
        } catch (IllegalArgumentException e) {
            System.out.println("初始餘額錯誤: " + e.getMessage() + "，將餘額設為0");
        }
        accountCount++; // 帳戶數量加1
    }

    public Account(String accountNumber, double initialBalance) {
        this(accountNumber, "未知", initialBalance);
    }

    public Account() {
        this("未知", "未知", 0);
    }

    public Account(String accountNumber) {

        this(accountNumber, "未知", 0);
    }

    /**
     * 取得帳戶號碼
     * @return 帳戶號碼
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * 取得帳戶餘額
     * @return 帳戶餘額
     */
    public double getBalance() {
        return balance;
    }

    /**
     * 取得帳戶擁有者名稱
     * @return 帳戶擁有者名稱
     */
    public String getOwnerName() {
        return ownerName;
    }

    /**
     * 設定帳戶餘額
     * @param balance 欲設定的帳戶餘額，必須為正數
     * @throws IllegalArgumentException 若餘額非正數則拋出例外
     */
    public void setBalance(double balance) {
        if (balance > 0) {
            this.balance = balance; // 設定新的帳戶餘額
        } else {
            throw new IllegalArgumentException("帳戶餘額必須為正數");
        }
    }

    /**
     * 設定帳戶號碼
     * @param accountNumber 欲設定的帳戶號碼
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 存款方法，將指定金額存入帳戶
     * @param amount 存入金額，必須為正數
     * @throws IllegalArgumentException 若金額非正數則拋出例外
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount; // 增加餘額
        } else {
            throw new IllegalArgumentException("存款金額必須為正數");
        }
    }

    /**
     * 提款方法，從帳戶扣除指定金額
     * @param amount 提款金額，必須為正數且不得超過餘額
     * @throws IllegalArgumentException 若金額不合法則拋出例外
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount; // 減少餘額
        } else {
            throw new IllegalArgumentException("提款金額不合法");
        }
    }

    public String toString() {
        return String.format("帳戶號碼: %s, 擁有者: %s, 餘額: %.2f, 開戶日期: %s, 開戶時間: %s",
                accountNumber, owner.toString(), balance, openingDate.toString(), openingTime.toUniversalString());
    }
}
