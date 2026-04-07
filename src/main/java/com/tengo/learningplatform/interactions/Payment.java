package com.tengo.learningplatform.interactions;

import java.math.BigDecimal;

import com.tengo.learningplatform.contracts.MoneyMovable;
import com.tengo.learningplatform.domain.PaymentChannel;


public class Payment extends Transaction implements MoneyMovable {

    private BigDecimal amount;
    private PaymentChannel channel;

    public Payment(BigDecimal amount) {
        this(amount, PaymentChannel.CARD);
    }

    public Payment(BigDecimal amount, PaymentChannel channel) {
        super();
        this.amount = amount;
        this.channel = channel != null ? channel : PaymentChannel.CARD;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public PaymentChannel getChannel() {
        return channel;
    }

    public void setChannel(PaymentChannel channel) {
        this.channel = channel != null ? channel : PaymentChannel.CARD;
    }

    @Override
    public String getTransactionType() {
        return "PAYMENT";
    }
}
