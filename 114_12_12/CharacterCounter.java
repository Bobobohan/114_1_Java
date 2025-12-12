import java.util.Scanner;

public class CharacterCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("請輸入一段文字: ");
        String input = scanner.nextLine();
        
        int letters = 0, digits = 0, spaces = 0, others = 0;
        
        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (Character.isLetter(ch)) {
                letters++;
            } else if (Character.isDigit(ch)) {
                digits++;
            } else if (Character.isWhitespace(ch)) {
                spaces++;
            } else {
                others++;
            }
        }
        
        System.out.println("\n統計結果:");
        System.out.println("字母數量: " + letters);
        System.out.println("數字數量: " + digits);
        System.out.println("空白數量: " + spaces);
        System.out.println("其他字元: " + others);
        
        scanner.close();
    }
}
