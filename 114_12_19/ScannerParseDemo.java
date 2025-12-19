import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ScannerParseDemo {
    public static void main(String[] args) {
        createTestFile();
        
        try (Scanner scanner = new Scanner(new File("scores.txt"))) {
            System.out.println("=== 成績資料 ===");
            
            while (scanner.hasNext()) {
                String name = scanner.next();      // 讀取字串
                int score = scanner.nextInt();     // 讀取整數
                double gpa = scanner.nextDouble(); // 讀取浮點數
                
                System.out.printf("%s: %d 分, GPA: %.2f%n", name, score, gpa);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private static void createTestFile() {
        try (PrintWriter pw = new PrintWriter("scores.txt")) {
            pw.println("王小明 85 3.5");
            pw.println("李小華 92 4.0");
            pw.println("張小美 78 3.2");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
