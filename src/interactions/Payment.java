package interactions;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

    private BigDecimal amount;
    private boolean isProcessed;
    private LocalDateTime processedAt;

    public Payment(BigDecimal amount) {
        this.amount = amount;
        this.isProcessed = false;
        this.processedAt = null;
    }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public boolean isProcessed() { return isProcessed; }
    public void setProcessed(boolean processed) { this.isProcessed = processed; }

    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }
}
