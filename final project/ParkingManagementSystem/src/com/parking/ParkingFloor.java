package com.parking;

import java.util.ArrayList;
import java.util.List;

public class ParkingFloor {
    // ========== 屬性 ==========
    private int floorLevel;
    private String floorName;
    private List<ParkingSpot> spots; // 使用 List 介面與 ArrayList 實作

    // ========== 建構子 ==========
    public ParkingFloor(int floorLevel, String floorName) {
        this.floorLevel = floorLevel;
        this.floorName = floorName;
        this.spots = new ArrayList<>(); // 初始為空清單
    }

    // ========== 行為方法 ==========

    /**
     * 新增車位至該樓層
     */
    public void addSpot(ParkingSpot spot) {
        if (spot != null) {
            spots.add(spot); //
        }
    }

    /**
     * 取得該層剩餘車位數
     */
    public int getAvailableSpotsCount() {
        int count = 0;
        for (ParkingSpot spot : spots) {
            if (!spot.isOccupied()) {
                count++;
            }
        }
        return count;
    }

    /**
     * 根據車種搜尋適合的空車位
     * 邏輯：
     * - Motorcycle: 搜尋 RegularSpot (小型) 或專屬位
     * - Sedan: 搜尋 RegularSpot
     * - SUV: 搜尋 LargeSpot (規格書中 LargeSpot 專供 SUV)
     * - Truck: 搜尋連續兩個標準位 (此處簡化搜尋邏輯)
     */
    public ParkingSpot findEmptySpot(Vehicle v) {
        for (int i = 0; i < spots.size(); i++) {
            ParkingSpot spot = spots.get(i);

            // 基本檢查：車位必須未被佔用
            if (spot.isOccupied()) continue;

            // 規則 A：貨車 (TRUCK) 邏輯
            if (v.getType() == VehicleType.TRUCK) {
                if (i + 1 < spots.size()) {
                    ParkingSpot nextSpot = spots.get(i + 1);
                    // 必須連續兩個都是 RegularSpot 且都未被佔用
                    if (spot instanceof RegularSpot && nextSpot instanceof RegularSpot && !nextSpot.isOccupied()) {
                        return spot; // 找到連續位的第一格
                    }
                }
                continue; // 若此格不符合貨車條件，跳過看下一格
            }

            // 規則 B：機車 (MOTORCYCLE) 邏輯
            if (v.getType() == VehicleType.MOTORCYCLE) {
                if (spot instanceof MotorcycleSpot) {
                    return spot;
                }
                // 規格書：若機車停入其他位，拋出例外
                // 注意：此處搜尋時若遇到非機車位會跳過，若全層都沒機車位，最後會由系統拋出「找不到車位」
                continue;
            }

            // 規則 C：身障車位 (HandicappedSpot) 邏輯
            // 任何非機車車種皆可停放，但進場前需由 ParkingLot 執行 verifyAccess
            if (spot instanceof HandicappedSpot) {
                return spot;
            }

            // 規則 D：專屬與一般位配對邏輯
            if (v.getType() == VehicleType.SEDAN) {
                if (spot instanceof RegularSpot) return spot;
            } else if (v.getType() == VehicleType.SUV) {
                // SUV 可停 RegularSpot 或專屬 LargeSpot
                if (spot instanceof RegularSpot || spot instanceof LargeSpot) return spot;
            } else if (v.getType() == VehicleType.MOTORCYCLE && spot instanceof MotorcycleSpot) {
                return spot;
            }
        }

        // 依照規格書：若無位子時回傳 null 或拋出指定訊息
        // 這裡我們維持原本的報錯機制以利 Main 攔截
        throw new IllegalStateException("找不到適合此類別車輛的車位");
    }

    // Getter 方法
    public int getFloorLevel() { return floorLevel; }
    public String getFloorName() { return floorName; }
    public List<ParkingSpot> getSpots() {
        return this.spots; // 假設您的車位變數名稱為 spots
    }
}
