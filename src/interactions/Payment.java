package interactions;

import java.math.BigDecimal;

public class Payment extends Transaction {

    private BigDecimal amount;

    public Payment(BigDecimal amount) {
        super();
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String getTransactionType() {
        return "PAYMENT";
    }
}
