package awin.task;

import awin.task.domain.TransactionEnricher.Transaction;

import java.util.List;

public interface TransactionRepository {
    List<Transaction> getAll();
}
