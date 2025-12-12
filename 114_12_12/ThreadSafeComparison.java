import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadSafeComparison {
    // 共享的字串建構器
    private static StringBuilder stringBuilder = new StringBuilder();
    private static StringBuffer stringBuffer = new StringBuffer();
    
    public static void main(String[] args) throws InterruptedException {
        // 測試 StringBuilder（非執行緒安全）
        testStringBuilder();
        
        // 重置
        stringBuilder = new StringBuilder();
        stringBuffer = new StringBuffer();
        
        // 測試 StringBuffer（執行緒安全）
        testStringBuffer();
    }
    
    private static void testStringBuilder() throws InterruptedException {
        System.out.println("=== StringBuilder 多執行緒測試 ===");
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                stringBuilder.append("A");
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        
        System.out.println("預期長度: 1000");
        System.out.println("實際長度: " + stringBuilder.length());
        System.out.println("資料可能損壞: " + (stringBuilder.length() != 1000));
    }
    
    private static void testStringBuffer() throws InterruptedException {
        System.out.println("\n=== StringBuffer 多執行緒測試 ===");
        ExecutorService executor = Executors.newFixedThreadPool(10);
        
        for (int i = 0; i < 1000; i++) {
            executor.submit(() -> {
                stringBuffer.append("A");
            });
        }
        
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.SECONDS);
        
        System.out.println("預期長度: 1000");
        System.out.println("實際長度: " + stringBuffer.length());
        System.out.println("資料正確: " + (stringBuffer.length() == 1000));
    }
}
