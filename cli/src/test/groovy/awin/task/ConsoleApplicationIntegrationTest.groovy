package awin.task

import awin.task.domain.TransactionEnricher
import spock.lang.Specification

import java.time.LocalDate
import java.time.Month

class ConsoleApplicationIntegrationTest extends Specification {
    def "should print transactions to standard output"() {
        given: "create console application jar"
        Runtime.getRuntime().exec("../mvnw package -DskipTests").waitFor()

        when: "execute jar"
        Process process = Runtime.getRuntime().exec("java -jar target/cli-1.0-SNAPSHOT.jar")

        then: "hardcoded data on output"
        def output = new String(process.getInputStream().readAllBytes())
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
}
