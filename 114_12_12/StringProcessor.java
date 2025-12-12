import java.util.Scanner;

public class StringProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 1. 反轉字串
        System.out.print("請輸入要反轉的字串: ");
        String input = scanner.nextLine();
        String reversed = reverseString(input);
        System.out.println("反轉後: " + reversed);
        
        // 2. 判斷迴文
        System.out.print("\n請輸入要檢查的字串: ");
        String palindromeCheck = scanner.nextLine();
        boolean isPalindrome = checkPalindrome(palindromeCheck);
        System.out.println("是否為迴文: " + isPalindrome);
        
        // 3. 字串壓縮
        System.out.print("\n請輸入要壓縮的字串: ");
        String toCompress = scanner.nextLine();
        String compressed = compressString(toCompress);
        System.out.println("壓縮後: " + compressed);
        
        scanner.close();
    }
    
    // 反轉字串
    public static String reverseString(String str) {
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }
    
    // 檢查迴文
    public static boolean checkPalindrome(String str) {
        str = str.toLowerCase().replaceAll("[^a-z0-9]", "");
        int left = 0, right = str.length() - 1;
        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
    
    // 字串壓縮 (例: "aabbcc" -> "a2b2c2")
    public static String compressString(String str) {
        if (str == null || str.length() <= 1) {
            return str;
        }
        
        StringBuilder compressed = new StringBuilder();
        int count = 1;
        
        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) == str.charAt(i - 1)) {
                count++;
            } else {
                compressed.append(str.charAt(i - 1));
                if (count > 1) {
                    compressed.append(count);
                }
                count = 1;
            }
        }
        
        // 處理最後一組字元
        compressed.append(str.charAt(str.length() - 1));
        if (count > 1) {
            compressed.append(count);
        }
        
        return compressed.toString();
    }
}
