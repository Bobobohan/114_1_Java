package com.parking;

import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Main {
    private static Map<String, Member> memberDatabase = new HashMap<>();
    private static Map<String, String> plateToMemberId = new HashMap<>();
    private static Map<String, String> ownerIdToMemberId = new HashMap<>();
    private static Map<String, VehicleRecord> activeVehicles = new HashMap<>();
    private static List<ParkingSession> parkingHistory = new ArrayList<>();

    private static Map<String, ReservationRecord> reservations = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ParkingLot lot = new ParkingLot("中正智慧系統", 60);
        ParkingFloor f1 = new ParkingFloor(1, "1F");
        ParkingFloor f2 = new ParkingFloor(2, "2F");

        for (int i = 1; i <= 5; i++) {
            f1.addSpot(new MotorcycleSpot("M-0" + i));
            f1.addSpot(new RegularSpot("R-0" + i));
            f1.addSpot(new LargeSpot("L-0" + i));
            f1.addSpot(new HandicappedSpot("H-0" + i));

            f2.addSpot(new MotorcycleSpot("M-20" + i));
            f2.addSpot(new RegularSpot("R-20" + i));
            f2.addSpot(new LargeSpot("L-20" + i));
        }
        lot.addFloor(f1);
        lot.addFloor(f2);

        preloadMembers();

        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║      🚗 停車場全邏輯機器人模擬啟動       ║");
        System.out.println("╚════════════════════════════════════════╝");

        while (true) {
            try {
                System.out.println("\n--- [請選擇操作選單] ---");
                System.out.println("1. 車輛進場 (含時間輸入與再進場判定)");
                System.out.println("2. 會員服務 (登入、註冊與月票管理)");
                System.out.println("3. 出場繳費 (分時精準計費與超時判定)");
                System.out.println("4. 預約車位 (專屬位分配與違約處理)");
                System.out.print(">> 選擇功能 (輸入 exit 結束): ");
                String stage = scanner.nextLine().trim();
                if (stage.equalsIgnoreCase("exit")) break;
                if (stage.isEmpty()) {
                    System.err.println("❌ 選項不可為空。");
                    continue;
                }

                switch (stage) {
                    case "1" -> processEntryStage(scanner, lot);
                    case "2" -> processMemberMenu(scanner);
                    case "3" -> processExitStage(scanner, lot);
                    case "4" -> processReservationStage(scanner, lot);
                    default -> System.err.println("❌ 無效選項，請重新輸入。");
                }
            } catch (Exception e) {
                System.err.println("\n❌ 規則判定攔截：" + e.getMessage());
            }
        }
        scanner.close();
    }

    private static class ReservationRecord {
        String name, ownerId, plate, spotId;
        LocalDateTime reservedTime;
        Vehicle vehicle;

        ReservationRecord(String name, String ownerId, String plate, String spotId, LocalDateTime time, Vehicle v) {
            this.name = name; this.ownerId = ownerId; this.plate = plate;
            this.spotId = spotId; this.reservedTime = time; this.vehicle = v;
        }
    }

    private static void cleanupExpiredReservations(LocalDateTime currentTime, ParkingLot lot) {
        Iterator<Map.Entry<String, ReservationRecord>> it = reservations.entrySet().iterator();
        while (it.hasNext()) {
            ReservationRecord res = it.next().getValue();
            if (currentTime.isAfter(res.reservedTime)) {
                lot.releaseSpot(res.spotId, res.vehicle.getType());
                System.out.println("📢 系統通知：車牌 " + res.plate + " 的預約逾期，已釋放車位 " + res.spotId);
                it.remove();
            }
        }
    }

    private static void processReservationStage(Scanner scanner, ParkingLot lot) {
        System.out.println("--- [開始預約車位流程] ---");
        String name = readNonEmptyInput(scanner, "請輸入車主姓名: ");
        String ownerId = readNonEmptyInput(scanner, "請輸入車主 ID: ");
        String plate = readLicensePlate(scanner, "請輸入預約車牌 (格式 ABC-1234): ");

        String nowStr = readNonEmptyInput(scanner, "請輸入現在時間 : ");
        LocalDateTime now = parseDateTime(nowStr);
        cleanupExpiredReservations(now, lot);

        String resStr = readNonEmptyInput(scanner, "請輸入預約時間 (必須為整點): ");
        if (!resStr.endsWith(".00")) throw new IllegalArgumentException("❌ 預約僅限整點時間。");
        LocalDateTime resTime = parseDateTime(resStr);

        if (resTime.isBefore(now)) throw new IllegalArgumentException("❌ 預約時間不可早於現在。");
        if (Duration.between(now, resTime).toHours() > 12) throw new IllegalArgumentException("❌ 預約時間不可晚於當下 12 小時。");

        boolean isH = readYesNo(scanner, "預約者是否具備身障資格?");

        System.out.print("選擇預約車種 (1:機車 2:房車 3:SUV 4:貨車): ");
        int type = Integer.parseInt(scanner.nextLine());
        Vehicle v = createVehicle(plate, type);

        ParkingSpot targetSpot = null;
        searchLoop:
        for (ParkingFloor floor : lot.getFloors()) {
            List<ParkingSpot> spots = floor.getSpots();
            if (isH) {
                for (ParkingSpot s : spots) {
                    if (s instanceof HandicappedSpot && !s.isOccupied()) {
                        if (v.getType() == VehicleType.TRUCK) {
                            String nextId = getNextId(s.getSpotId());
                            ParkingSpot ns = lot.getAllSpots().get(nextId);
                            if (ns != null && !ns.isOccupied()) {
                                targetSpot = s; break searchLoop;
                            }
                        } else {
                            targetSpot = s; break searchLoop;
                        }
                    }
                }
            }

            for (ParkingSpot s : spots) {
                if (!s.isOccupied()) {
                    try {
                        s.validateVehicleAccess(v);
                        if (v.getType() == VehicleType.TRUCK) {
                            String nextId = getNextId(s.getSpotId());
                            ParkingSpot ns = lot.getAllSpots().get(nextId);
                            if (ns != null && ns instanceof RegularSpot && !ns.isOccupied()) {
                                targetSpot = s; break searchLoop;
                            }
                        } else {
                            targetSpot = s; break searchLoop;
                        }
                    } catch (Exception e) { continue; }
                }
            }
        }

        if (targetSpot == null) throw new IllegalStateException("❌ 找不到適合此類別的空車位。");

        targetSpot.setOccupied(true);
        String displayId = targetSpot.getSpotId();
        if (v.getType() == VehicleType.TRUCK) {
            String nextId = getNextId(displayId);
            lot.getAllSpots().get(nextId).setOccupied(true);
            displayId += " & " + nextId;
        }

        reservations.put(plate, new ReservationRecord(name, ownerId, plate, targetSpot.getSpotId(), resTime, v));
        System.out.println("✅ 預約成功！專屬車位: " + displayId);
    }

    private static void processEntryStage(Scanner scanner, ParkingLot lot) {
        System.out.println("--- [車輛進場資訊填寫] ---");
        String timeStr = readNonEmptyInput(scanner, "請輸入進場時間 : ");
        LocalDateTime entryTime = parseDateTime(timeStr);

        String nameInput = readNonEmptyInput(scanner, "請輸入車主姓名: ");
        String ownerId = readNonEmptyInput(scanner, "請輸入車主 ID: ");
        String plate = readLicensePlate(scanner, "請輸入車牌號碼 (格式 ABC-1234): ");

        String mid = plateToMemberId.get(plate);
        Member mem = (mid != null) ? memberDatabase.get(mid) : null;
        boolean finalIsH = (mem != null) ? mem.isHandicapped() : readYesNo(scanner, "是否具備身障資格?");

        // --- 設定優化：預約進場車種連動 ---
        if (reservations.containsKey(plate)) {
            ReservationRecord res = reservations.get(plate);
            if (res.name.equals(nameInput) && res.ownerId.equals(ownerId)) {
                if (entryTime.isAfter(res.reservedTime)) {
                    System.err.println("⚠️ 警告：已過預約時間！");
                    System.out.println("💰 須現金繳納違約金 200 元。");
                    double paid = 0;
                    while (paid < 200) {
                        System.out.print("請投入現金 (尚欠 " + (200 - paid) + " 元): ");
                        try {
                            paid += Double.parseDouble(scanner.nextLine());
                        } catch (NumberFormatException e) { System.err.println("❌ 請輸入數字。"); }
                    }
                    lot.releaseSpot(res.spotId, res.vehicle.getType());
                    reservations.remove(plate);
                    if (!readYesNo(scanner, "是否繼續正常進場流程?")) return;
                } else {
                    String finalId = res.spotId;
                    // 使用預約時儲存的 Vehicle 對象，確保計費一致
                    if (res.vehicle.getType() == VehicleType.TRUCK) {
                        finalId += " & " + getNextId(finalId);
                    }
                    System.out.println("✨ 預約進場成功！車位: " + finalId);
                    registerActiveVehicle(res.name, ownerId, res.vehicle, entryTime, finalId, finalIsH);
                    reservations.remove(plate);
                    return;
                }
            }
        }

        long inheritedHours = 0;
        String targetTicketId = "TK-" + plate;
        for (int i = parkingHistory.size() - 1; i >= 0; i--) {
            if (parkingHistory.get(i).getTicketId().equals(targetTicketId)) {
                if (Duration.between(parkingHistory.get(i).getExitTime(), entryTime).toHours() < 2) {
                    inheritedHours = parkingHistory.get(i).getTotalDurationUsed().toHours();
                    System.out.println("🔄 繼承前次時數: " + inheritedHours + " 小時。");
                }
                break;
            }
        }

        System.out.print("確認車種 (1:機車 2:房車 3:SUV 4:貨車): ");
        int type = Integer.parseInt(scanner.nextLine());
        Vehicle v = createVehicle(plate, type);

        ParkingSpot assigned = null;
        if (finalIsH) {
            for (ParkingFloor f : lot.getFloors()) {
                for (ParkingSpot s : f.getSpots()) {
                    if (s instanceof HandicappedSpot && !s.isOccupied()) {
                        if (v.getType() == VehicleType.TRUCK) {
                            String nextId = getNextId(s.getSpotId());
                            ParkingSpot ns = lot.getAllSpots().get(nextId);
                            if (ns != null && !ns.isOccupied()) {
                                assigned = s; assigned.setOccupied(true);
                                ns.setOccupied(true); break;
                            }
                        } else {
                            assigned = s; assigned.setOccupied(true); break;
                        }
                    }
                }
                if (assigned != null) break;
            }
        }

        if (assigned == null) {
            ParkingTicket ticket = lot.processEntry(v);
            assigned = ticket.getAssignedSpot();
        }

        String spotId = assigned.getSpotId();
        String displayId = spotId;
        if (v.getType() == VehicleType.TRUCK) {
            displayId += " & " + getNextId(assigned.getSpotId());
        }

        registerActiveVehicle(nameInput, ownerId, v, entryTime, displayId, finalIsH);
        activeVehicles.get(plate).accumulatedHours = inheritedHours;
        System.out.println("✅ 進場成功！分配車位：" + displayId);
    }

    private static String getNextId(String spotId) {
        int dash = spotId.lastIndexOf("-");
        String prefix = spotId.substring(0, dash + 1);
        int nextNum = Integer.parseInt(spotId.substring(dash + 1)) + 1;
        return prefix + String.format("%02d", nextNum);
    }

    private static void registerActiveVehicle(String name, String ownerId, Vehicle v, LocalDateTime time, String spot, boolean isH) {
        String mid = plateToMemberId.get(v.getLicensePlate());
        boolean isMem = (mid != null && mid.equals(ownerIdToMemberId.get(ownerId)));
        VehicleRecord rec = new VehicleRecord(name, ownerId, v, isH, isMem, false, spot, mid);
        rec.entryTime = time;
        activeVehicles.put(v.getLicensePlate(), rec);
    }

    private static void processMemberRegistration(Scanner scanner) {
        System.out.println("--- [開始新會員註冊流程] ---");
        String name = readNonEmptyInput(scanner, "姓名: ");
        String oid = readNonEmptyInput(scanner, "車主 ID: ");
        String plt = readLicensePlate(scanner, "車牌 (格式 ABC-1234): ");

        if (reservations.containsKey(plt)) {
            ReservationRecord res = reservations.get(plt);
            if (res.name.equals(name) && res.ownerId.equals(oid) && LocalDateTime.now().isAfter(res.reservedTime)) {
                System.err.println("❌ 先繳預約罰金！");
                return;
            }
        }

        String mid = readNonEmptyInput(scanner, "會員 ID: ");
        boolean isH = readYesNo(scanner, "身障?");
        registerMemberToDB(mid, name, plt, oid, isH, isH ? readDisabilityId(scanner) : null);
        System.out.println("✅ 註冊成功。");
    }

    private static String readLicensePlate(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.matches("^[A-Z]{3}-\\d{4}$")) return input;
            System.err.println("❌ 格式錯誤 (範例: ABC-1234)");
        }
    }

    private static String readNonEmptyInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.err.println("❌ 輸入不可為空白。");
        }
    }

    private static LocalDateTime parseDateTime(String input) {
        try {
            return LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy.MM.dd.HH.mm"));
        } catch (Exception e) {
            throw new IllegalArgumentException("時間格式錯誤。");
        }
    }

    private static boolean readYesNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("y")) return true;
            if (input.equals("n")) return false;
        }
    }

    private static String readDisabilityId(Scanner scanner) {
        while (true) {
            String id = readNonEmptyInput(scanner, "請輸入身障編號 : ");
            if (id.matches("^[ab]\\d{5}$")) return id;
        }
    }

    private static void preloadMembers() {
        registerMemberToDB("M888", "測試車主", "ABC-8888", "OID-888", true, "a11111");
    }

    private static void registerMemberToDB(String memberId, String name, String plate, String ownerId, boolean isHandicapped, String dId) {
        Member m = new Member(memberId, name);
        m.setHandicapped(isHandicapped);
        if (dId != null) m.setDisabilityCardId(dId);
        memberDatabase.put(memberId, m);
        plateToMemberId.put(plate, memberId);
        ownerIdToMemberId.put(ownerId, memberId);
    }

    private static void processMemberMenu(Scanner scanner) {
        System.out.println("\n--- [會員服務系統] ---");
        System.out.println("1. 會員登入 / 加值\n2. 新會員註冊\n3. 購買月票");
        String choice = readNonEmptyInput(scanner, ">> 請選擇功能: ");
        if (choice.equals("1")) processMemberLogin(scanner);
        else if (choice.equals("2")) processMemberRegistration(scanner);
        else if (choice.equals("3")) processBuyMonthlyPass(scanner);
    }

    private static void processMemberLogin(Scanner scanner) {
        String mid = readNonEmptyInput(scanner, "會員 ID: ");
        if (!memberDatabase.containsKey(mid)) return;
        Member m = memberDatabase.get(mid);
        String name = readNonEmptyInput(scanner, "車主姓名: ");
        String oid = readNonEmptyInput(scanner, "車主 ID: ");
        if (!m.getName().equals(name) || !mid.equals(ownerIdToMemberId.get(oid))) {
            System.err.println("❌ 驗證失敗。"); return;
        }
        System.out.println("👋 歡迎回來，餘額: " + Math.round(m.getBalance()) + " 元");
        if (readYesNo(scanner, "加值?")) {
            try {
                m.setBalance(m.getBalance() + Double.parseDouble(readNonEmptyInput(scanner, "金額: ")));
            } catch (Exception e) { System.err.println("❌ 加值失敗，請輸入有效數字。"); }
        }
    }

    private static void processBuyMonthlyPass(Scanner scanner) {
        String mid = readNonEmptyInput(scanner, "會員 ID: ");
        if (!memberDatabase.containsKey(mid)) return;
        Member m = memberDatabase.get(mid);
        if (m.isHasMonthlyPass()) return;
        while (m.getBalance() < 2000) {
            if (readYesNo(scanner, "餘額不足，加值?")) {
                try {
                    m.setBalance(m.getBalance() + Double.parseDouble(readNonEmptyInput(scanner, "金額: ")));
                } catch (Exception e) { System.err.println("❌ 請輸入有效數字。"); }
            } else return;
        }
        m.deductBalance(2000.0);
        m.setHasMonthlyPass(true);
        System.out.println("✅ 購買成功。");
    }

    private static void processExitStage(Scanner scanner, ParkingLot lot) {
        System.out.println("\n--- [開始出場繳費程序] ---");
        String plate = readLicensePlate(scanner, "請輸入離場車牌號碼 (格式 ABC-1234): ");
        if (!activeVehicles.containsKey(plate)) throw new IllegalArgumentException("無效車牌");

        VehicleRecord record = activeVehicles.get(plate);
        String exitTimeStr = readNonEmptyInput(scanner, "離場時間: ");
        LocalDateTime exitTime = parseDateTime(exitTimeStr);
        if (exitTime.isBefore(record.entryTime)) throw new IllegalArgumentException("❌ 時間錯誤");

        System.out.println("📝 確認資訊: 車主 " + record.ownerName + " | 車位: " + record.spotDesc);

        Duration duration = Duration.between(record.entryTime, exitTime);
        long hoursInSession = (duration.toMinutes() + 59) / 60;
        long totalHoursInLot = record.accumulatedHours + hoursInSession;

        double standardBaseRate = record.vehicle.getHourlyRate();
        double effectiveTimeBasedFee = 0;
        for (int i = 0; i < hoursInSession; i++) {
            LocalDateTime hourToCheck = record.entryTime.plusHours(i);
            double currentHourRate = standardBaseRate;
            if (hourToCheck.getDayOfWeek() == DayOfWeek.SATURDAY || hourToCheck.getDayOfWeek() == DayOfWeek.SUNDAY) currentHourRate *= 1.5;
            if (hourToCheck.getHour() >= 23 || hourToCheck.getHour() < 7) currentHourRate *= 0.8;
            effectiveTimeBasedFee += currentHourRate;
        }

        double baseFee = record.isHandicapped ? effectiveTimeBasedFee * 0.5 : effectiveTimeBasedFee;

        double penaltyFee = 0;
        if (totalHoursInLot > 12) {
            long prevOT = Math.max(0, record.accumulatedHours - 12);
            long currOT = Math.max(0, totalHoursInLot - 12);
            if (currOT > prevOT) penaltyFee = (currOT - prevOT) * 100.0;
        }

        String mId = (record.memberId != null && !record.memberId.isEmpty()) ? record.memberId : plateToMemberId.get(plate);
        boolean isMember = mId != null && memberDatabase.containsKey(mId);
        long finalFeeValue = 0;

        if (isMember) {
            Member m = memberDatabase.get(mId);
            if (m.isHasMonthlyPass()) finalFeeValue = Math.round(penaltyFee);
            else finalFeeValue = Math.round((baseFee * 0.85) + penaltyFee);

            if (finalFeeValue > 0) {
                while (m.getBalance() < finalFeeValue) {
                    if (readYesNo(scanner, "餘額不足 (" + finalFeeValue + " 元)，加值?")) {
                        try {
                            m.setBalance(m.getBalance() + Double.parseDouble(readNonEmptyInput(scanner, "金額: ")));
                        } catch (Exception e) { System.err.println("❌ 請輸入有效數字。"); }
                    } else throw new IllegalStateException("支付中斷，請完成繳費後離場");
                }
                m.deductBalance((double) finalFeeValue);
                System.out.println("✅ 已扣款: " + finalFeeValue + " 元。");
            }
        } else {
            finalFeeValue = Math.round(baseFee + penaltyFee);
            System.out.println("💰 應繳總額: " + finalFeeValue);
            double totalPaid = 0;
            while (totalPaid < finalFeeValue) {
                System.out.print("請支付現金 (尚欠 " + (finalFeeValue - totalPaid) + " 元): ");
                try {
                    totalPaid += Double.parseDouble(scanner.nextLine());
                } catch (Exception e) { System.err.println("❌ 請輸入數字。"); }
            }
            if (totalPaid > finalFeeValue) System.out.println("🪙 找零: " + Math.round(totalPaid - finalFeeValue));
        }

        if (readYesNo(scanner, "確認離場?")) {
            // --- 設定優化：加強貨車釋放安全性 ---
            String mainSpot = record.spotDesc.contains(" & ") ? record.spotDesc.split(" & ")[0] : record.spotDesc;
            lot.releaseSpot(mainSpot, record.vehicle.getType());

            parkingHistory.add(new ParkingSession("TK-" + plate, exitTime, (double)finalFeeValue, "PAID"));
            parkingHistory.get(parkingHistory.size()-1).setTotalDurationUsed(Duration.ofHours(totalHoursInLot));
            activeVehicles.remove(plate);
            System.out.println("✨ 離場成功，一路平安！");
        }
    }

    private static String formatSpotType(ParkingSpot spot) {
        if (spot instanceof HandicappedSpot) return "(身障車位)";
        if (spot instanceof MotorcycleSpot) return "(機車車位)";
        if (spot instanceof LargeSpot) return "(大型車位)";
        return "(一般車位)";
    }

    private static Vehicle createVehicle(String plate, int type) {
        return switch (type) {
            case 1 -> new Motorcycle(plate);
            case 2 -> new Sedan(plate);
            case 3 -> new SUV(plate);
            case 4 -> new Truck(plate);
            default -> throw new IllegalArgumentException("無效車種");
        };
    }

    private static class VehicleRecord {
        final String ownerName, ownerId, spotDesc, memberId;
        final Vehicle vehicle;
        final boolean isHandicapped, isMember, hasMonthlyPass;
        LocalDateTime entryTime;
        long accumulatedHours = 0;
        VehicleRecord(String name, String id, Vehicle v, boolean h, boolean m, boolean mp, String sd, String mid) {
            this.ownerName = name; this.ownerId = id; this.vehicle = v;
            this.isHandicapped = h; this.isMember = m; this.hasMonthlyPass = mp;
            this.spotDesc = sd; this.memberId = mid;
        }
    }
}