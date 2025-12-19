import java.io.File;
import java.io.FilenameFilter;

public class ListDirectoryDemo {
    public static void main(String[] args) {
        // 使用 "." 代表當前工作目錄
        File dir = new File(".");
        
        // =============================================
        // 方法一：list() - 取得檔案名稱（字串陣列）
        // =============================================
        System.out.println("=== list() 方法 ===");
        String[] names = dir.list();
        
        // 重要：list() 可能回傳 null！
        if (names != null) {
            for (String name : names) {
                System.out.println(name);
            }
        }
        
        // =============================================
        // 方法二：listFiles() - 取得 File 物件陣列
        // =============================================
        System.out.println("\n=== listFiles() 方法 ===");
        File[] files = dir.listFiles();
        
        if (files != null) {
            for (File f : files) {
                String type = f.isDirectory() ? "[目錄]" : "[檔案]";
                System.out.printf("%s %s (%d bytes)%n", 
                    type, f.getName(), f.length());
            }
        }
        
        // =============================================
        // 方法三：list(FilenameFilter) - 過濾特定檔案
        // =============================================
        System.out.println("\n=== 過濾 .java 檔案 ===");
        
        // 傳統寫法：匿名內部類別
        String[] javaFiles = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".java");
            }
        });
        
        // Lambda 簡化寫法（Java 8+）
        String[] javaFilesLambda = dir.list((d, name) -> name.endsWith(".java"));
        
        if (javaFiles != null) {
            for (String name : javaFiles) {
                System.out.println(name);
            }
        }
    }
}
