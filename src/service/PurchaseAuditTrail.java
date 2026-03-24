package service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public class PurchaseAuditTrail implements AutoCloseable {

    private final BufferedWriter writer;

    private PurchaseAuditTrail(BufferedWriter writer) {
        this.writer = writer;
    }

    public static PurchaseAuditTrail openDefault() {
        Path path = Path.of("logs", "purchase_audit.log");
        try {
            Files.createDirectories(path.getParent());
            BufferedWriter bw = Files.newBufferedWriter(
                    path,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
            return new PurchaseAuditTrail(bw);
        } catch (IOException e) {
            throw new UncheckedIOException("Could not open purchase audit log", e);
        }
    }

    public void appendEvent(String message) throws IOException {
        writer.write(LocalDateTime.now().toString());
        writer.write(" | ");
        writer.write(message);
        writer.newLine();
    }

    @Override
    public void close() throws IOException {
        writer.flush();
        writer.close();
    }
}
