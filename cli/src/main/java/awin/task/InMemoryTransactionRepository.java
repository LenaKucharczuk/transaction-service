package awin.task;

import awin.task.domain.TransactionEnricher;
import awin.task.domain.TransactionEnricher.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Repository
public class InMemoryTransactionRepository implements TransactionRepository {
    private final List<Transaction> transactions = sampleTransactions();

    @Override
    public List<Transaction> getAll() {
        return transactions;
    }

    private static List<Transaction> sampleTransactions() {
        return List.of(
            new Transaction(
                0L,
                LocalDate.of(2022, Month.FEBRUARY, 3),
                List.of(
                    new TransactionEnricher.Product("Harry", BigDecimal.TEN),
                    new TransactionEnricher.Product("Potter", BigDecimal.ONE)
                )
            ),
            new Transaction(
                1L,
                LocalDate.of(2022, Month.FEBRUARY, 13),
                List.of(
                    new TransactionEnricher.Product("Jest", new BigDecimal("10.12")),
                    new TransactionEnricher.Product("Najlepszy", new BigDecimal("110.12"))
                )
            )
        );
    }
}
