package domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum PaymentChannel {

    CARD(new BigDecimal("0.029")) {
        {
            assert getFeeRate().signum() > 0;
        }

        @Override
        public String receiptTag() {
            return "CARD";
        }
    },
    BANK_TRANSFER(new BigDecimal("0.00")) {
        {
            assert BigDecimal.ZERO.compareTo(getFeeRate()) == 0;
        }

        @Override
        public String receiptTag() {
            return "ACH";
        }
    },
    PLATFORM_CREDIT(new BigDecimal("0.00")) {
        {
            assert BigDecimal.ZERO.compareTo(getFeeRate()) == 0;
        }

        @Override
        public String receiptTag() {
            return "CREDIT";
        }
    };

    static {
        System.out.println("[domain] PaymentChannel fee table ready.");
    }

    private final BigDecimal feeRate;

    PaymentChannel(BigDecimal feeRate) {
        this.feeRate = feeRate;
    }

    public BigDecimal getFeeRate() {
        return feeRate;
    }

    public abstract String receiptTag();

    public BigDecimal estimatedProcessingFee(BigDecimal grossAmount) {
        if (grossAmount == null || grossAmount.signum() <= 0) {
            return BigDecimal.ZERO;
        }
        return grossAmount.multiply(feeRate).setScale(2, RoundingMode.HALF_UP);
    }
}
