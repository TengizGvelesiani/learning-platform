package exceptions;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {

    private final BigDecimal required;
    private final BigDecimal offered;

    public InsufficientFundsException(BigDecimal required, BigDecimal offered) {
        super(String.format("Insufficient funds: need %s but payment is %s", required, offered));
        this.required = required;
        this.offered = offered;
    }

    public BigDecimal getRequired() {
        return required;
    }

    public BigDecimal getOffered() {
        return offered;
    }
}
