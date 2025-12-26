package com.parking;
import java.time.Duration;

public class MemberPricing implements PricingStrategy {
    private PricingStrategy baseStrategy;

    public MemberPricing(PricingStrategy baseStrategy) {
        this.baseStrategy = baseStrategy;
    }

    @Override
    public double calculateFee(Duration duration, Vehicle vehicle) {
        // 總金額 85 折
        return baseStrategy.calculateFee(duration, vehicle) * 0.85;
    }

    @Override
    public String getDescription() {
        return "會員專屬折扣";
    }
}
