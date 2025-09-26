import java.util.Scanner;

public class ClassAverage {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int total = 0;   //總分
        int gradeCounter = 0;   //成績計數器

        System.out.print(" 輸入完成請按ctrl-z (Windows) 或 ctrl-d (Mac/Linus) 結束輸入");

        while (input.hasNext()) {
            if (input.hasNextInt()) {
                int grade = input.nextInt();   //讀取成績
                total +=+ grade;   //累加總分
                gradeCounter++;    //成績計數器+1
            }else  {
                System.out.println("輸入錯誤，請輸入整數成績!");
                input.next();   //跳括錯誤輸入
            }

        }

        if (gradeCounter != 0) {
            double average = (double) total/ gradeCounter;   //計算平均值
            System.out.printf("總分: %d%n", total);
            System.out.printf("成績數: %d%n", gradeCounter);
            System.out.printf("平均成績: %.2f%n", average);
        }else {
            System.out.println("沒有輸入任何成績");
        }
    }
}
