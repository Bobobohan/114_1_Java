import java.util.regex.*;

public class RegexBasics {
    public static void main(String[] args) {
        // 1. 簡單匹配
        String text = "The price is $99.99";
        String pattern = "\\$\\d+\\.\\d{2}";
        
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        
        if (m.find()) {
            System.out.println("找到價格: " + m.group());
        }
        
        // 2. 多重匹配
        String emails = "Contact: john@email.com, mary@company.org";
        String emailPattern = "\\b[\\w.-]+@[\\w.-]+\\.\\w{2,}\\b";
        
        Pattern emailP = Pattern.compile(emailPattern);
        Matcher emailM = emailP.matcher(emails);
        
        System.out.println("\n找到的Email:");
        while (emailM.find()) {
            System.out.println("- " + emailM.group());
        }
        
        // 3. 群組擷取
        String date = "Today is 2024-03-15";
        String datePattern = "(\\d{4})-(\\d{2})-(\\d{2})";
        
        Pattern dateP = Pattern.compile(datePattern);
        Matcher dateM = dateP.matcher(date);
        
        if (dateM.find()) {
            System.out.println("\n日期分析:");
            System.out.println("完整日期: " + dateM.group(0));
            System.out.println("年: " + dateM.group(1));
            System.out.println("月: " + dateM.group(2));
            System.out.println("日: " + dateM.group(3));
        }
    }
}
