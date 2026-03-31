package domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum PaymentChannel {

    CARD(new BigDecimal("0.029"), "CARD"),
    BANK_TRANSFER(new BigDecimal("0.00"), "ACH"),
    PLATFORM_CREDIT(new BigDecimal("0.00"), "CREDIT");

    static {
        System.out.println("[domain] PaymentChannel fee table ready.");
    }

    private final BigDecimal feeRate;
    private final String receiptTag;

    PaymentChannel(BigDecimal feeRate, String receiptTag) {
        this.feeRate = feeRate;
        this.receiptTag = receiptTag;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public String receiptTag() {
        return receiptTag;
    }

    public BigDecimal estimatedProcessingFee(BigDecimal grossAmount) {
        if (grossAmount == null || grossAmount.signum() <= 0) {
            return BigDecimal.ZERO;
        }
        return grossAmount.multiply(feeRate).setScale(2, RoundingMode.HALF_UP);
    }
}
