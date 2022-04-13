package awin.task

import awin.task.domain.ProductBuilder
import awin.task.domain.TransactionBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static awin.task.domain.TransactionEnricher.EnrichedTransaction

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TransactionControllerIntegrationTest extends Specification {

    @Autowired
    private TestRestTemplate httpClient

    def "should get enriched transactions"() {
        given:
        def first = new TransactionBuilder().withProducts([
            new ProductBuilder().withCost("10.28").build()
        ]).build()
        def second = new TransactionBuilder().withProducts([
            new ProductBuilder().withCost("60.28").build(),
            new ProductBuilder().withCost("30.28").build()
        ]).build()

        when:
        ResponseEntity<List<EnrichedTransaction>> response = httpClient.exchange(
            "/enrich/transactions",
            HttpMethod.POST,
            new HttpEntity<>([first, second]),
            new ParameterizedTypeReference<List<EnrichedTransaction>>() {}
        )

        then:
        response.statusCode == HttpStatus.OK
        response.body == [
            new EnrichedTransaction(first, new BigDecimal("10.28")),
            new EnrichedTransaction(second, new BigDecimal("90.56"))
        ]
    }

    def "should get enriched transaction"() {
        given:
        def transaction =
            new TransactionBuilder().withProducts([new ProductBuilder().withCost("10.28").build()]).build()

        when:
        ResponseEntity<EnrichedTransaction> response = httpClient.postForEntity(
            "/enrich/transaction",
            new HttpEntity<>(transaction),
            EnrichedTransaction.class
        )

        then:
        response.statusCode == HttpStatus.OK
        response.body == new EnrichedTransaction(transaction, new BigDecimal("10.28"))
    }

    def "should return error when enriching transaction without products"() {
        given:
        def transaction =
            new TransactionBuilder().withProducts([]).build()

        when:
        ResponseEntity<String> response = httpClient.postForEntity(
            "/enrich/transaction",
            new HttpEntity<>(transaction),
            String.class
        )

        then:
        response.statusCode == HttpStatus.BAD_REQUEST
        response.body == "Products cannot be empty for transaction"
    }
}
