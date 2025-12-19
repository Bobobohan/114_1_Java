import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CharStreamBasicDemo {
    public static void main(String[] args) {
        // =============================================
        // 【寫入文字檔】使用 FileWriter
        // =============================================
        try (FileWriter writer = new FileWriter("message.txt")) {
            // 直接寫入字串（FileOutputStream 做不到！）
            writer.write("第一行文字\n");
            writer.write("第二行文字\n");
            writer.write("中文字元處理正確！");
            System.out.println("寫入完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // =============================================
        // 【讀取文字檔】使用 FileReader
        // =============================================
        try (FileReader reader = new FileReader("message.txt")) {
            int ch;
            System.out.print("讀取內容: ");
            // read() 每次讀取一個字元（不是位元組）
            while ((ch = reader.read()) != -1) {
                System.out.print((char) ch);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
