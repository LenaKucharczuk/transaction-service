package awin.task

import awin.task.domain.TransactionEnricher
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class ConsoleApplicationIntegrationTest extends Specification {

    def "should print transactions to standard output"() {
        when: "run console application"
        def output = captureStandardOutputOf { ConsoleApplication.main() }

        then: "standard output contains hardcoded transactions"
        output.contains(
            new TransactionEnricher.Transaction(
                0L,
                LocalDate.of(2022, Month.FEBRUARY, 3),
                List.of(
                    new TransactionEnricher.Product("Harry", BigDecimal.TEN),
                    new TransactionEnricher.Product("Potter", BigDecimal.ONE)
                )
            ).toString()
        )
        output.contains(
            new TransactionEnricher.Transaction(
                1L,
                LocalDate.of(2022, Month.FEBRUARY, 13),
                List.of(
                    new TransactionEnricher.Product("Jest", new BigDecimal("10.12")),
                    new TransactionEnricher.Product("Najlepszy", new BigDecimal("110.12"))
                )
            ).toString()
        )
    }

    static String captureStandardOutputOf(Closure function) {
        ByteArrayOutputStream captured = new ByteArrayOutputStream()
        PrintStream old = System.out
        System.setOut(new PrintStream(captured))
        function()
        System.out.flush()
        System.setOut(old)
        return captured.toString()
    }
}
