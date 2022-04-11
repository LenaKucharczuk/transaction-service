package awin.task;

import awin.task.domain.TransactionEnricher;
import awin.task.domain.TransactionEnricher.EnrichedTransaction;
import awin.task.domain.TransactionEnricher.Transaction;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/enrich")
public class TransactionController {
    private final TransactionEnricher transactionEnricher;

    public TransactionController(TransactionEnricher transactionEnricher) {
        this.transactionEnricher = transactionEnricher;
    }

    @PostMapping("/transactions")
    List<EnrichedTransaction> enrichedTransactions(@RequestBody List<Transaction> transactions) {
        return transactionEnricher.enrichWithTransactionCost(transactions);
    }

    @PostMapping("/transaction")
    EnrichedTransaction enrichedTransaction(@RequestBody Transaction transaction) {
        return transactionEnricher.enrichWithTransactionCost(List.of(transaction))
            .get(0);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException e) {
        return e.getMessage();
    }
}
