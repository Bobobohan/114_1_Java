import java.util.Scanner;
import java.util.function.DoublePredicate;

public class Account {
    // 帳戶號碼
    private String accountNumber;
    // 帳戶餘額
    private double balance;


    // 單一 Scanner，共用 System.in
    private static final Scanner IN = new Scanner(System.in);

    /**
     * 建構子，初始化帳戶號碼與初始餘額
     * @param accountNumber 帳戶號碼
     * @param initialBalance 初始餘額
     */
    public Account(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        try {
            setBalance(initialBalance);
        } catch (IllegalArgumentException e) {
            System.out.println("初始餘額錯誤: " + e.getMessage() + "，餘額設為0");
            this.balance = 0;
        }
    }

    public Account() {
        this("000000", 0.0);
    }

    public Account(String accountNumber) {
        this(accountNumber, 0.0);
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }

    /**
     * 設定帳戶餘額
     * @param amount 帳戶餘額
     */
    public void setBalance(double amount) {
        if (amount > 0) {
            this.balance = amount;
            return;
        }
        // 若傳入不合法，互動要求正確金額
        this.balance = getValidAmount(IN, "請輸入帳戶餘額：", x -> x > 0, "帳戶餘額必須為正數");
        // 不關閉 IN (System.in)
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * 存款方法，將指定金額存入帳戶
     * @param amount 存入金額，必須為正數
     */
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            return;
        }
        balance += getValidAmount(IN, "請輸入存款金額：", x -> x > 0, "存款金額必須為正數");
    }

    /**
     * 擴充存款方法，支援多種貨幣
     * @param amount 存入金額
     * @param currency 貨幣種類，如 TWD、USD、EUR、JPY
     */
    public void deposit (double amount, String currency){
        double exchangeRate;
        switch (currency.toUpperCase()){
            case "USD":
                exchangeRate = 30.0; // 假設1 USD = 30 TWD
                break;
            case "EUR":
                exchangeRate = 35.0; // 假設1 EUR = 35 TWD
                break;
            case "JPY":
                exchangeRate = 0.2; // 假設1 JPY = 0.20 TWD
                break;
            default:
                System.out.println("不支援的貨幣，請使用 TWD、USD、EUR 或 JPY");
                return;
        }
        double amountInTWD = amount * exchangeRate;
        this.deposit(amountInTWD);
    }

    /**
     * 擴充存款方法，支援多筆金額一次存入
     * @param amounts 多筆存入金額
     */
    public void deposit(double ... amounts) {
        double total = 0;
        for (double amount : amounts) {
            if (amount >= 0) {
                total += amount;
            } else {
                throw new IllegalArgumentException("存款金額須為正數");
            }
        }
        this.deposit(total);
    }

    /**
     * 提款方法，從帳戶中扣除指定金額
     * @param amount 提款金額，必須為正數且不得超過餘額
     */
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            return;
        }
        balance -= getValidAmount(IN, "請輸入提款金額：", x -> x > 0 && x <= balance, "提款金額必須為正數且不得超過餘額");
    }

    /**
     * 互動取得有效的金額輸入
     * @param scanner 輸入用 Scanner
     * @param prompt 提示訊息
     * @param validator 驗證條件
     * @param errorMessage 錯誤訊息
     * @return 有效的金額
     */
    private double getValidAmount(Scanner scanner, String prompt, DoublePredicate validator, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            if (!scanner.hasNextDouble()) {
                String bad = scanner.next();
                System.out.println("輸入非數字: " + bad);
                continue;
            }
            double v = scanner.nextDouble();
            if (validator.test(v)) return v;
            System.out.println(errorMessage);
        }
    }
}
