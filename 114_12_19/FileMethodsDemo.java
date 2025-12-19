import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileMethodsDemo {
    public static void main(String[] args) {
        File file = new File("test.txt");
        File dir = new File("myFolder");
        
        // =============================================
        // 一、檢查類方法 - 用於判斷檔案/目錄的狀態
        // =============================================
        System.out.println("=== 檔案檢查 ===");
        
        // exists() - 檢查檔案或目錄是否存在
        // 回傳 true 表示存在，false 表示不存在
        System.out.println("是否存在: " + file.exists());
        
        // isFile() - 檢查是否為「檔案」（不是目錄）
        // 注意：如果檔案不存在，也會回傳 false
        System.out.println("是否為檔案: " + file.isFile());
        
        // isDirectory() - 檢查是否為「目錄」
        // 用來區分檔案和資料夾
        System.out.println("是否為目錄: " + file.isDirectory());
        
        // canRead() - 檢查程式是否有讀取權限
        // 取決於作業系統的檔案權限設定
        System.out.println("是否可讀: " + file.canRead());
        
        // canWrite() - 檢查程式是否有寫入權限
        // 唯讀檔案會回傳 false
        System.out.println("是否可寫: " + file.canWrite());
        
        // isHidden() - 檢查是否為隱藏檔
        // Windows: 檔案屬性設為隱藏
        // Linux/Mac: 檔名以 . 開頭
        System.out.println("是否為隱藏檔: " + file.isHidden());
        
        // =============================================
        // 二、檔案資訊方法 - 取得檔案的各種屬性
        // =============================================
        System.out.println("\n=== 檔案資訊 ===");
        
        // getName() - 只取得檔案名稱（不含路徑）
        // 例如：C:/data/example.txt → example.txt
        System.out.println("檔案名稱: " + file.getName());
        
        // getAbsolutePath() - 取得完整的絕對路徑
        // 即使建立時使用相對路徑，此方法也會回傳絕對路徑
        System.out.println("絕對路徑: " + file.getAbsolutePath());
        
        // getParent() - 取得父目錄路徑
        // 例如：C:/data/example.txt → C:/data
        // 注意：若使用相對路徑且無父目錄，可能回傳 null
        System.out.println("父目錄: " + file.getParent());
        
        // length() - 取得檔案大小（單位：bytes）
        // 注意：對於目錄，回傳值沒有意義
        System.out.println("檔案大小: " + file.length() + " bytes");
        
        // lastModified() - 取得最後修改時間
        // 回傳值是 Unix timestamp（毫秒），需轉換為可讀格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long timestamp = file.lastModified();
        System.out.println("最後修改: " + sdf.format(new Date(timestamp)));
        
        // =============================================
        // 三、建立與刪除方法 - 實際操作檔案系統
        // =============================================
        System.out.println("\n=== 檔案操作 ===");
        try {
            // createNewFile() - 建立新檔案
            // 回傳值：true = 檔案不存在，成功建立; false = 檔案已存在
            if (file.createNewFile()) {
                System.out.println("檔案建立成功");
            } else {
                System.out.println("檔案已存在，未建立新檔");
            }
            
            // mkdir() - 建立單層目錄
            // 只能建立一層目錄，父目錄必須存在
            if (dir.mkdir()) {
                System.out.println("目錄建立成功");
            }
            
            // mkdirs() - 建立多層目錄（推薦）
            // 會自動建立所有不存在的父目錄
            File multiDir = new File("parent/child/grandchild");
            if (multiDir.mkdirs()) {
                System.out.println("多層目錄建立成功");
            }
            
            // renameTo() - 重新命名或移動檔案
            File newFile = new File("renamed.txt");
            if (file.renameTo(newFile)) {
                System.out.println("重新命名成功");
            }
            
            // delete() - 刪除檔案或空目錄
            // 注意：只能刪除空目錄，非空目錄會失敗
            // newFile.delete();
            
        } catch (IOException e) {
            System.out.println("操作失敗: " + e.getMessage());
        }
    }
}
