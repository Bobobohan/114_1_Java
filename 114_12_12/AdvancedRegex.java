import java.util.regex.*;

public class AdvancedRegex {
    public static void main(String[] args) {
        // 1. 貪婪 vs 非貪婪匹配
        String html = "<div>Content1</div><div>Content2</div>";
        
        // 貪婪匹配（預設）
        Pattern greedy = Pattern.compile("<div>.*</div>");
        Matcher greedyM = greedy.matcher(html);
        if (greedyM.find()) {
            System.out.println("貪婪: " + greedyM.group());
        }
        
        // 非貪婪匹配
        Pattern lazy = Pattern.compile("<div>.*?</div>");
        Matcher lazyM = lazy.matcher(html);
        System.out.println("非貪婪:");
        while (lazyM.find()) {
            System.out.println("- " + lazyM.group());
        }
        
        // 2. 前瞻與後顧
        String password = "MyPassword123!";
        
        // 正向前瞻 - 密碼必須包含數字
        String strongPassword = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        boolean isStrong = password.matches(strongPassword);
        System.out.println("\n密碼強度檢查: " + isStrong);
        
        // 3. 命名群組 (Java 7+)
        String log = "2024-03-15 14:30:45 ERROR Database connection failed";
        String logPattern = "(?<date>\\d{4}-\\d{2}-\\d{2}) " +
                           "(?<time>\\d{2}:\\d{2}:\\d{2}) " +
                           "(?<level>\\w+) " +
                           "(?<message>.+)";
        
        Pattern logP = Pattern.compile(logPattern);
        Matcher logM = logP.matcher(log);
        
        if (logM.find()) {
            System.out.println("\n日誌分析:");
            System.out.println("日期: " + logM.group("date"));
            System.out.println("時間: " + logM.group("time"));
            System.out.println("等級: " + logM.group("level"));
            System.out.println("訊息: " + logM.group("message"));
        }
    }
}
