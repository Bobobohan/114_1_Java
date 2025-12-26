package com.parking;

import java.util.*;
import java.time.*;

public class ParkingLot {
    private String name;
    private ArrayList<ParkingFloor> floors = new ArrayList<>();
    private int maxCapacity;
    private int currentVehicleCount = 0;
    private Map<String, ParkingSpot> allSpots = new HashMap<>();

    public ParkingLot(String name, int maxCapacity) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("停車場名稱不可為空或 null");
        }
        this.name = name;
        this.maxCapacity = maxCapacity;
    }

    /**
     * 車輛進場核心邏輯：支援一般分配與貨車連續位分配
     */
    public ParkingTicket processEntry(Vehicle v) {
        if (isFull()) {
            throw new IllegalStateException("停車場已滿，目前無可用車位");
        }

        for (ParkingFloor floor : floors) {
            List<ParkingSpot> floorSpots = floor.getSpots();
            for (int i = 0; i < floorSpots.size(); i++) {
                ParkingSpot spot = floorSpots.get(i);

                if (!spot.isOccupied()) {
                    try {
                        // 1. 基礎車種驗證
                        spot.validateVehicleAccess(v);

                        // 2. 針對貨車的特殊連續位邏輯 (確保 R-01 下一格必須是 R-02)
                        if (v.getType() == VehicleType.TRUCK) {
                            String nextId = getNextSpotId(spot.getSpotId());
                            ParkingSpot nextSpot = allSpots.get(nextId);

                            if (nextSpot != null && (nextSpot instanceof RegularSpot) && !nextSpot.isOccupied()) {
                                // 同時鎖定兩格
                                spot.setOccupied(true);
                                nextSpot.setOccupied(true);
                                currentVehicleCount += 2;

                                System.out.println("🚛 貨車分配成功：已同時鎖定連續車位 " + spot.getSpotId() + " & " + nextId);
                                return createTicket(v, spot, spot.getSpotId() + " & " + nextId);
                            } else {
                                continue; // 連續位不成立，找下一個起始點
                            }
                        }

                        // 3. 一般車輛佔用
                        spot.setOccupied(true);
                        currentVehicleCount++;
                        return createTicket(v, spot, spot.getSpotId());

                    } catch (IllegalArgumentException e) {
                        continue; // 車位類型不符
                    }
                }
            }
        }
        throw new IllegalStateException("找不到適合此類別車輛的車位");
    }

    /**
     * 輔助方法：產生票券
     */
    private ParkingTicket createTicket(Vehicle v, ParkingSpot spot, String displayId) {
        String ticketId = "TK-" + v.getLicensePlate() + "-" + UUID.randomUUID().toString().substring(0, 4);
        return new ParkingTicket(ticketId, v, spot);
    }

    /**
     * 計算下一個連續編號 (例如 R-01 -> R-02)
     */
    private String getNextSpotId(String currentId) {
        try {
            int dashIndex = currentId.lastIndexOf("-");
            String prefix = currentId.substring(0, dashIndex + 1);
            int num = Integer.parseInt(currentId.substring(dashIndex + 1));
            return prefix + String.format("%02d", num + 1);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 釋放車位 (用於離場或逾期)
     */
    public void releaseSpot(String spotId, VehicleType type) {
        ParkingSpot spot = allSpots.get(spotId);
        if (spot != null) {
            spot.setOccupied(false);
            if (type == VehicleType.TRUCK) {
                ParkingSpot nextSpot = allSpots.get(getNextSpotId(spotId));
                if (nextSpot != null) nextSpot.setOccupied(false);
                currentVehicleCount = Math.max(0, currentVehicleCount - 2);
            } else {
                currentVehicleCount = Math.max(0, currentVehicleCount - 1);
            }
        }
    }

    public void addFloor(ParkingFloor floor) {
        if (floor != null) {
            floors.add(floor);
            for (ParkingSpot spot : floor.getSpots()) {
                allSpots.put(spot.getSpotId(), spot);
            }
        }
    }

    public List<ParkingFloor> getFloors() { return floors; }
    public Map<String, ParkingSpot> getAllSpots() { return allSpots; }
    public boolean isFull() { return currentVehicleCount >= maxCapacity; }
    public String getName() { return name; }
}