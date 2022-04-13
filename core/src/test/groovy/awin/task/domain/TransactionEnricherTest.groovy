package awin.task.domain

import spock.lang.Specification

import static awin.task.domain.TransactionEnricher.EnrichedTransaction
import static awin.task.domain.TransactionEnricher.Transaction

class TransactionEnricherTest extends Specification {
    TransactionEnricher transactionService = new TransactionEnricher()

    def "should sum cost of each transaction products"() {
        given:
        def first = new TransactionBuilder()
            .withProducts([
                new ProductBuilder().withCost("10.23").build(),
                new ProductBuilder().withCost("110.53").build()
            ]).build()
        def second = new TransactionBuilder()
            .withProducts([
                new ProductBuilder().withCost("90.23").build()
            ]).build()

        when:
        def enrichedTransactions = transactionService.enrichWithTransactionCosts([first, second])

        then:
        enrichedTransactions == [
            new EnrichedTransaction(first, new BigDecimal("120.76")),
            new EnrichedTransaction(second, new BigDecimal("90.23"))
        ]
    }

    def "should throw exception when provided transaction with empty products"() {
        given:
        def invalid = new TransactionBuilder().withProducts([]).build()
        def valid = new TransactionBuilder().build()

        when:
        transactionService.enrichWithTransactionCosts([invalid, valid])

        then:
        def exception = thrown(IllegalArgumentException)
        exception.message == "Products cannot be empty for transaction"
    }

    def "should return empty result when no transaction was passed to enrich"() {
        given:
        List<Transaction> empty = []

        when:
        def enrichedTransactions = transactionService.enrichWithTransactionCosts(empty)

        then:
        enrichedTransactions == []
    }
}


