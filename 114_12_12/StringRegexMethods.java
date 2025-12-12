public class StringRegexMethods {
    public static void main(String[] args) {
        // matches() - 完整匹配
        String phone = "0912-345-678";
        boolean isPhone = phone.matches("\\d{4}-\\d{3}-\\d{3}");
        System.out.println("是否為電話格式: " + isPhone);
        
        // replaceAll() - 取代所有符合的部分
        String text = "Java123Python456JavaScript";
        String result = text.replaceAll("\\d+", "-");
        System.out.println("取代數字: " + result);
        
        // replaceFirst() - 只取代第一個
        String first = text.replaceFirst("\\d+", "-");
        System.out.println("取代第一個: " + first);
        
        // split() - 使用正規表示法分割
        String data = "apple,banana;orange:grape";
        String[] fruits = data.split("[,:;]");
        System.out.println("\n分割結果:");
        for (String fruit : fruits) {
            System.out.println("- " + fruit);
        }
    }
}
