package com.parking;
import java.time.LocalDateTime; // 必須導入以支援 LocalDateTime
import java.time.Duration;      // 必須導入以支援 Duration

public interface Reservable {
    void reserve(LocalDateTime start, Duration duration);
    void cancelReservation(String reservationId);
    boolean isAvailableAt(LocalDateTime time);
}
