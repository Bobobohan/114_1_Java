import java.util.*;
import java.util.regex.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class LogEntry {
    private LocalDateTime timestamp;
    private String level;
    private String message;
    private String source;
    
    public LogEntry(LocalDateTime timestamp, String level, String source, String message) {
        this.timestamp = timestamp;
        this.level = level;
        this.source = source;
        this.message = message;
    }
    
    // Getters
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getLevel() { return level; }
    public String getMessage() { return message; }
    public String getSource() { return source; }
    
    @Override
    public String toString() {
        return String.format("[%s] %s - %s: %s", 
            timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
            level, source, message);
    }
}

public class LogAnalyzer {
    // 日誌格式: [2024-03-15 14:30:45] ERROR - DatabaseModule: Connection timeout
    private static final String LOG_PATTERN = 
        "\\[(\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2})\\] " +
        "(\\w+) - ([\\w]+): (.+)";
    
    private List<LogEntry> logs = new ArrayList<>();
    
    public void parseLogFile(String[] logLines) {
        Pattern pattern = Pattern.compile(LOG_PATTERN);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        
        for (String line : logLines) {
            Matcher matcher = pattern.matcher(line);
            if (matcher.find()) {
                LocalDateTime timestamp = LocalDateTime.parse(matcher.group(1), formatter);
                String level = matcher.group(2);
                String source = matcher.group(3);
                String message = matcher.group(4);
                
                logs.add(new LogEntry(timestamp, level, source, message));
            }
        }
    }
    
    public void analyzeErrors() {
        System.out.println("\n=== 錯誤分析 ===");
        Map<String, Integer> errorCount = new HashMap<>();
        
        for (LogEntry log : logs) {
            if ("ERROR".equals(log.getLevel())) {
                errorCount.merge(log.getSource(), 1, Integer::sum);
            }
        }
        
        errorCount.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue() + " errors"));
    }
    
    public void findPatterns(String searchPattern) {
        System.out.println("\n=== 搜尋模式: " + searchPattern + " ===");
        Pattern pattern = Pattern.compile(searchPattern, Pattern.CASE_INSENSITIVE);
        
        for (LogEntry log : logs) {
            Matcher matcher = pattern.matcher(log.getMessage());
            if (matcher.find()) {
                System.out.println(log);
            }
        }
    }
    
    public void generateReport() {
        System.out.println("\n=== 日誌統計報告 ===");
        
        // 統計各級別日誌數量
        Map<String, Long> levelCount = new HashMap<>();
        for (LogEntry log : logs) {
            levelCount.merge(log.getLevel(), 1L, Long::sum);
        }
        
        System.out.println("日誌級別統計:");
        levelCount.forEach((level, count) -> 
            System.out.println("  " + level + ": " + count));
        
        // 找出最常出現的錯誤訊息
        Map<String, Integer> messageFreq = new HashMap<>();
        Pattern errorPattern = Pattern.compile("(\\w+Exception|\\w+Error)");
        
        for (LogEntry log : logs) {
            if ("ERROR".equals(log.getLevel())) {
                Matcher matcher = errorPattern.matcher(log.getMessage());
                while (matcher.find()) {
                    messageFreq.merge(matcher.group(1), 1, Integer::sum);
                }
            }
        }
        
        if (!messageFreq.isEmpty()) {
            System.out.println("\n常見錯誤類型:");
            messageFreq.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue()));
        }
    }
    
    public static void main(String[] args) {
        // 模擬日誌資料
        String[] sampleLogs = {
            "[2024-03-15 10:15:30] INFO - ApplicationModule: Application started",
            "[2024-03-15 10:16:45] ERROR - DatabaseModule: SQLException: Connection timeout",
            "[2024-03-15 10:17:20] DEBUG - NetworkModule: Request sent to API endpoint",
            "[2024-03-15 10:18:10] ERROR - DatabaseModule: SQLException: Table not found",
            "[2024-03-15 10:19:55] WARNING - SecurityModule: Invalid login attempt detected",
            "[2024-03-15 10:20:30] ERROR - NetworkModule: IOException: Network unreachable",
            "[2024-03-15 10:21:15] INFO - ApplicationModule: Processing user request",
            "[2024-03-15 10:22:00] ERROR - DatabaseModule: SQLException: Deadlock detected",
            "[2024-03-15 10:23:45] ERROR - FileModule: FileNotFoundException: Config file missing",
            "[2024-03-15 10:24:30] INFO - ApplicationModule: Request completed successfully"
        };
        
        LogAnalyzer analyzer = new LogAnalyzer();
        
        // 解析日誌
        analyzer.parseLogFile(sampleLogs);
        
        // 執行分析
        analyzer.analyzeErrors();
        analyzer.findPatterns("Exception");
        analyzer.generateReport();
        
        // 互動式查詢
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("\n輸入要搜尋的模式 (輸入 'quit' 結束): ");
            String input = scanner.nextLine();
            if ("quit".equalsIgnoreCase(input)) break;
            analyzer.findPatterns(input);
        }
        scanner.close();
    }
}
