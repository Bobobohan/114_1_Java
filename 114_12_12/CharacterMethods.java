public class CharacterMethods {
    public static void main(String[] args) {
        char ch = 'a';
        
        // 判斷字元類型
        System.out.println("是否為字母: " + Character.isLetter(ch));
        System.out.println("是否為數字: " + Character.isDigit('5'));
        System.out.println("是否為空白: " + Character.isWhitespace(' '));
        System.out.println("是否為大寫: " + Character.isUpperCase('A'));
        System.out.println("是否為小寫: " + Character.isLowerCase(ch));
        
        // 字元轉換
        System.out.println("轉大寫: " + Character.toUpperCase(ch));
        System.out.println("轉小寫: " + Character.toLowerCase('A'));
        
        // 取得字元數值
        System.out.println("數字字元的值: " + Character.getNumericValue('9'));
    }
}
