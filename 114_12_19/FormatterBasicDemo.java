import java.io.FileNotFoundException;
import java.util.Formatter;

public class FormatterBasicDemo {
    public static void main(String[] args) {
        try (Formatter formatter = new Formatter("output.txt")) {
            formatter.format("=== 學生成績單 ===%n");
            formatter.format("%n");
            formatter.format("%-10s %5s %8s%n", "姓名", "分數", "等級");
            formatter.format("%-10s %5d %8s%n", "王小明", 85, "B+");
            formatter.format("%-10s %5d %8s%n", "李小華", 92, "A");
            formatter.format("%-10s %5d %8s%n", "張小美", 78, "C+");
            
            System.out.println("檔案寫入完成！");
            
        } catch (FileNotFoundException e) {
            System.out.println("無法建立檔案: " + e.getMessage());
        }
    }
}
