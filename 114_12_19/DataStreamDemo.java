import java.io.*;

public class DataStreamDemo {
    public static void main(String[] args) {
        String filename = "data.bin";
        
        // =============================================
        // 【寫入資料】使用 DataOutputStream
        // =============================================
        // DataOutputStream 必須「包裝」另一個 OutputStream
        try (DataOutputStream dos = new DataOutputStream(
                new FileOutputStream(filename))) {
            
            // writeInt(int) - 寫入 4 bytes 的整數
            dos.writeInt(12345);
            
            // writeDouble(double) - 寫入 8 bytes 的浮點數
            dos.writeDouble(3.14159);
            
            // writeBoolean(boolean) - 寫入 1 byte
            dos.writeBoolean(true);
            
            // writeUTF(String) - 寫入 UTF-8 編碼的字串
            dos.writeUTF("你好，世界！");
            
            System.out.println("資料寫入完成");
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // =============================================
        // 【讀取資料】使用 DataInputStream
        // =============================================
        try (DataInputStream dis = new DataInputStream(
                new FileInputStream(filename))) {
            
            // 【關鍵】讀取順序必須與寫入順序「完全一致」！
            int intValue = dis.readInt();
            double doubleValue = dis.readDouble();
            boolean boolValue = dis.readBoolean();
            String strValue = dis.readUTF();
            
            System.out.println("讀取的資料:");
            System.out.println("int: " + intValue);
            System.out.println("double: " + doubleValue);
            System.out.println("boolean: " + boolValue);
            System.out.println("String: " + strValue);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
