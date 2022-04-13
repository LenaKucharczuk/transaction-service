package awin.task.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TransactionEnricher {

    public List<EnrichedTransaction> enrichWithTransactionCosts(List<Transaction> transactions) {
        return transactions.stream()
            .map(TransactionEnricher::sumProductCosts)
            .toList();
    }

    private static EnrichedTransaction sumProductCosts(Transaction transaction) {
        BigDecimal cost = transaction.products()
            .stream()
            .map(Product::cost)
            .reduce(BigDecimal::add)
            .orElseThrow(() -> new IllegalArgumentException("Products cannot be empty for transaction"));
        return new EnrichedTransaction(transaction, cost);
    }

    public static record Product(String name, BigDecimal cost) {
    }

    public static record Transaction(Long id, LocalDate saleDate, List<Product> products) {
    }

    public static record EnrichedTransaction(Transaction transaction, BigDecimal cost) {
    }
}
