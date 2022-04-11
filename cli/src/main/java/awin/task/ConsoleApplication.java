package awin.task;

import awin.task.domain.TransactionEnricher;
import awin.task.domain.TransactionEnricher.EnrichedTransaction;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class ConsoleApplication implements CommandLineRunner {

    private final TransactionRepository transactionRepository;

    public ConsoleApplication(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<EnrichedTransaction> transactions = new TransactionEnricher()
            .enrichWithTransactionCost(transactionRepository.getAll());
        System.out.println(transactions);
    }
}