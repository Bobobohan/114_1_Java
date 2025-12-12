public class StringMethods {
    public static void main(String[] args) {
        String str = "  Hello Java Programming  ";
        
        // 長度與位置
        System.out.println("長度: " + str.length());
        System.out.println("第5個字元: " + str.charAt(5));
        System.out.println("Java的位置: " + str.indexOf("Java"));
        System.out.println("最後一個g的位置: " + str.lastIndexOf("g"));
        
        // 子字串
        System.out.println("子字串(7, 11): " + str.substring(7, 11));
        System.out.println("從第7個開始: " + str.substring(7));
        
        // 大小寫轉換
        System.out.println("轉大寫: " + str.toUpperCase());
        System.out.println("轉小寫: " + str.toLowerCase());
        
        // 去除空白
        System.out.println("去除前後空白: [" + str.trim() + "]");
        
        // 字串替換
        System.out.println("替換: " + str.replace("Java", "Python"));
        
        // 字串分割
        String data = "apple,banana,orange";
        String[] fruits = data.split(",");
        for (String fruit : fruits) {
            System.out.println("水果: " + fruit);
        }
        
        // 字串比較
        String s1 = "abc";
        String s2 = "ABC";
        System.out.println("equals: " + s1.equals(s2));
        System.out.println("equalsIgnoreCase: " + s1.equalsIgnoreCase(s2));
        System.out.println("compareTo: " + s1.compareTo(s2));
        
        // 檢查開頭結尾
        String filename = "document.pdf";
        System.out.println("是否為pdf: " + filename.endsWith(".pdf"));
        System.out.println("是否以doc開頭: " + filename.startsWith("doc"));
    }
}
