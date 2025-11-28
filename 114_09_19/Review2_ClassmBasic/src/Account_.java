public class Account_ {
    //帳戶密碼，唯一識別每個帳戶
    private String accountNumber;
    //帳戶餘額
    private double balance;

    /**
     * 建構子，初始化帳戶號碼與初始餘額
     * @param accountNumber  帳戶餘額
     * @param initialBalance  初始餘額
     */
    public Account_(String accountNumber, double initialBalance) {
        this.setAccountNumber(accountNumber);
        try {
            this.setBalance(initialBalance);
        } catch (IllegalArgumentException e) {
            System.out.println("初始餘額錯誤: " + e.getMessage() + "，餘額設為0");
        }

    }

    /**
     * 取得帳戶號碼
     * @return  帳戶號碼
     */
    public String getAccountNumber() { return accountNumber; }

    /**
     * 取得帳戶餘額
     * @return  帳戶餘額
     */
    public double getBalance() { return balance;}

    public void setBalance(double amount) {
        if (balance > 0) {
            balance += amount;
        } else {
            throw new IllegalArgumentException("帳戶餘額必須為正數");
        }
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 存款方法，將指定金額存入帳戶
     * @param amount 存入金額，必須為正數
     * @throws IllegalArgumentException  若金額非正數則拋出例外
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            throw new IllegalArgumentException("存款金額必須為正數");
        }
    }

    /**
     * 提款方法，從帳戶中扣除指定金額
     * @param amount  提款金額，必須為正數且不得超過餘額
     * @throws IllegalArgumentException  若金額不合法則拋出例外
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            throw new IllegalArgumentException("提款金額不合法");
        }
    }
}