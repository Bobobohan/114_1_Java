import java.util.Scanner;
import java.util.regex.*;

public class DataValidator {
    // 定義驗證模式
    private static final String EMAIL_PATTERN = 
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    
    private static final String PHONE_PATTERN = 
        "^09\\d{8}$|^\\(0\\d\\)\\d{7,8}$";
    
    private static final String ID_PATTERN = 
        "^[A-Z][12]\\d{8}$";
    
    private static final String URL_PATTERN = 
        "^(https?|ftp)://[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.println("\n=== 資料驗證工具 ===");
            System.out.println("1. 驗證 Email");
            System.out.println("2. 驗證電話號碼");
            System.out.println("3. 驗證身分證字號");
            System.out.println("4. 驗證網址");
            System.out.println("5. 擷取HTML標籤內容");
            System.out.println("6. 清理特殊字元");
            System.out.println("0. 結束");
            System.out.print("請選擇功能: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // 清除換行
            
            if (choice == 0) break;
            
            switch (choice) {
                case 1:
                    System.out.print("請輸入Email: ");
                    String email = scanner.nextLine();
                    System.out.println("驗證結果: " + validateEmail(email));
                    break;
                    
                case 2:
                    System.out.print("請輸入電話號碼: ");
                    String phone = scanner.nextLine();
                    System.out.println("驗證結果: " + validatePhone(phone));
                    break;
                    
                case 3:
                    System.out.print("請輸入身分證字號: ");
                    String id = scanner.nextLine();
                    System.out.println("驗證結果: " + validateID(id));
                    break;
                    
                case 4:
                    System.out.print("請輸入網址: ");
                    String url = scanner.nextLine();
                    System.out.println("驗證結果: " + validateURL(url));
                    break;
                    
                case 5:
                    System.out.print("請輸入HTML: ");
                    String html = scanner.nextLine();
                    extractHTMLTags(html);
                    break;
                    
                case 6:
                    System.out.print("請輸入要清理的文字: ");
                    String dirty = scanner.nextLine();
                    System.out.println("清理後: " + cleanText(dirty));
                    break;
            }
        }
        
        scanner.close();
    }
    
    public static boolean validateEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
    public static boolean validatePhone(String phone) {
        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    
    public static boolean validateID(String id) {
        Pattern pattern = Pattern.compile(ID_PATTERN);
        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
    }
    
    public static boolean validateURL(String url) {
        Pattern pattern = Pattern.compile(URL_PATTERN);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }
    
    public static void extractHTMLTags(String html) {
        // 擷取所有HTML標籤的內容
        Pattern pattern = Pattern.compile("<(\\w+)[^>]*>(.*?)</\\1>");
        Matcher matcher = pattern.matcher(html);
        
        System.out.println("找到的標籤內容:");
        while (matcher.find()) {
            System.out.println("標籤: " + matcher.group(1) + 
                             ", 內容: " + matcher.group(2));
        }
    }
    
    public static String cleanText(String text) {
        // 移除非字母數字的特殊字元，保留空格和中文
        return text.replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5\\s]", "");
    }
}
