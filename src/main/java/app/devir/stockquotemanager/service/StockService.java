package app.devir.stockquotemanager.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.devir.stockquotemanager.entity.StockEntity;
import app.devir.stockquotemanager.entity.dto.Stock;
import app.devir.stockquotemanager.exception.StockQuoteException;
import app.devir.stockquotemanager.repository.StockRepository;

@Service
public class StockService {

    private static final Logger LOGGER = LogManager.getLogger(StockService.class);

    @Autowired
    private StockRepository repository;

    public void save(Stock stock) throws StockQuoteException {
        StockEntity entity = new StockEntity();

        try {
            entity.setId(stock.getId());
            Map<LocalDate, BigDecimal> quotes = stock.getQuotes().entrySet().stream().collect(
                    Collectors.toMap(e -> LocalDate.parse(e.getKey()), e -> (new BigDecimal((String) e.getValue()))));

            entity.setQuotes(quotes);
            repository.save(entity);
        } catch (NumberFormatException | DateTimeParseException e) {
            LOGGER.error(e);
            throw new StockQuoteException(e.getMessage());
        }

    }

    public List<StockEntity> findAll() {
        return repository.findAll();
    }

    public Optional<StockEntity> findById(String id) {
        return repository.findById(id);
    }
    
}
