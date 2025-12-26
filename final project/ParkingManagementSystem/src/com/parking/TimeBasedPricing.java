package com.parking;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * TimeBasedPricing - 分時進階計費策略
 * 支援每小時獨立判定：假日 1.5 倍、夜間 0.8 倍。
 */
public class TimeBasedPricing implements PricingStrategy {

    @Override
    public double calculateFee(Duration duration, Vehicle vehicle) {
        // 規則：未滿一小時以一小時計
        long totalHours = (duration.toMinutes() + 59) / 60;
        double standardRate = vehicle.getHourlyRate();
        double totalFee = 0;

        // 以「現在（出場時間）」往前推算每一小時的時段
        LocalDateTime currentTime = LocalDateTime.now();

        for (int i = 0; i < totalHours; i++) {
            LocalDateTime hourToCheck = currentTime.minusHours(i);
            double currentHourRate = standardRate;

            // 1. 判定假日：費率 * 1.5
            if (isWeekend(hourToCheck)) {
                currentHourRate *= 1.5;
            }

            // 2. 判定夜間 (23:00-07:00)：費率 * 0.8
            // 注意：若同時為假日與夜間，會疊加計算
            if (isNightTime(hourToCheck)) {
                currentHourRate *= 0.8;
            }

            totalFee += currentHourRate;
        }

        return totalFee;
    }

    @Override
    public String getDescription() {
        return "分時進階計費 (含假日/夜間判定)";
    }

    // 週末判定：週六(6) 與 週日(7)
    private boolean isWeekend(LocalDateTime time) {
        int day = time.getDayOfWeek().getValue();
        return day == 6 || day == 7;
    }

    // 夜間判定：23:00 以後 或 07:00 以前
    private boolean isNightTime(LocalDateTime time) {
        int hour = time.getHour();
        return hour >= 23 || hour < 7;
    }
}