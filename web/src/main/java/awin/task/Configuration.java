package awin.task;

import awin.task.domain.TransactionEnricher;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    public TransactionEnricher transactionService() {
        return new TransactionEnricher();
    }
}
