package app.devir.stockquotemanager.configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import app.devir.stockquotemanager.client.StockQuoteManagerClient;

@Component
public class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        StockQuoteManagerClient client = new StockQuoteManagerClient();
        try {
            client.requestNotification();
            LOGGER.info("Request notification to client on startup");
        } catch (Exception e) {
            LOGGER.info("Fail to request notification to client on startup");
        }
    }
}
