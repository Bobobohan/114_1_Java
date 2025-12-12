public class StringBuilderDemo {
    public static void main(String[] args) {
        // StringBuilder - 非執行緒安全，效能較好
        StringBuilder sb = new StringBuilder();
        sb.append("Hello");
        sb.append(" ");
        sb.append("World");
        sb.insert(5, ",");
        sb.replace(0, 5, "Hi");
        sb.delete(2, 3);
        System.out.println("StringBuilder: " + sb.toString());
        
        // StringBuffer - 執行緒安全，適合多執行緒環境
        StringBuffer sbf = new StringBuffer();
        sbf.append("Java");
        sbf.append(" ");
        sbf.append("Programming");
        sbf.insert(4, " 8");
        sbf.replace(0, 4, "Python");
        sbf.reverse();  // 反轉字串
        System.out.println("StringBuffer: " + sbf.toString());
        sbf.reverse();  // 再次反轉回來
        
        // 效能比較
        long startTime = System.currentTimeMillis();
        String str = "";
        for (int i = 0; i < 10000; i++) {
            str += "a";  // 每次創建新物件
        }
        long endTime = System.currentTimeMillis();
        System.out.println("String 串接時間: " + (endTime - startTime) + "ms");
        
        startTime = System.currentTimeMillis();
        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < 10000; i++) {
            sb2.append("a");  // 修改同一物件
        }
        endTime = System.currentTimeMillis();
        System.out.println("StringBuilder 串接時間: " + (endTime - startTime) + "ms");
        
        startTime = System.currentTimeMillis();
        StringBuffer sbf2 = new StringBuffer();
        for (int i = 0; i < 10000; i++) {
            sbf2.append("a");  // 修改同一物件（有同步）
        }
        endTime = System.currentTimeMillis();
        System.out.println("StringBuffer 串接時間: " + (endTime - startTime) + "ms");
    }
}
