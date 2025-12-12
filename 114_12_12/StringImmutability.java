public class StringImmutability {
    public static void main(String[] args) {
        String str1 = "Hello";
        String str2 = str1;
        
        str1 = str1 + " World";  // 創建新字串物件
        
        System.out.println("str1 = " + str1);  // Hello World
        System.out.println("str2 = " + str2);  // Hello
        
        // 字串池（String Pool）
        String s1 = "Java";
        String s2 = "Java";
        String s3 = new String("Java");
        
        System.out.println("s1 == s2: " + (s1 == s2));  // true
        System.out.println("s1 == s3: " + (s1 == s3));  // false
        System.out.println("s1.equals(s3): " + s1.equals(s3));  // true
    }
}
