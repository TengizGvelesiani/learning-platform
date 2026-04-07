package com.tengo.learningplatform.exceptions;

import java.math.BigDecimal;


public class InsufficientFundsException extends RuntimeException {

    public InsufficientFundsException(BigDecimal required, BigDecimal offered) {
        super(String.format("Insufficient funds: need %s but payment is %s", required, offered));
    }
}
