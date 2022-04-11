package awin.task.domain

import groovy.transform.builder.Builder
import groovy.transform.builder.SimpleStrategy

import java.time.LocalDate
import java.time.Month

import static awin.task.domain.TransactionEnricher.Product
import static awin.task.domain.TransactionEnricher.Transaction

@Builder(builderStrategy = SimpleStrategy, prefix = 'with')
class ProductBuilder {
    String name = "Harry Potter jest najlepszy"
    String cost = "10.00"

    Product build() {
        return new Product(name, new BigDecimal(cost))
    }
}

@Builder(builderStrategy = SimpleStrategy, prefix = 'with')
class TransactionBuilder {
    Long id = 0L
    List<Product> products = [new ProductBuilder().build()]
    LocalDate saleDate = LocalDate.of(2022, Month.FEBRUARY, 3)

    Transaction build() {
        return new Transaction(id, saleDate, products)
    }
}