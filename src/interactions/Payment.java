package interactions;

import contracts.MoneyMovable;
import java.math.BigDecimal;

public class Payment extends Transaction implements MoneyMovable {

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
