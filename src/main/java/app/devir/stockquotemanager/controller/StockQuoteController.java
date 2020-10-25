package app.devir.stockquotemanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.devir.stockquotemanager.client.StockQuoteManagerClient;
import app.devir.stockquotemanager.entity.StockEntity;
import app.devir.stockquotemanager.entity.dto.Stock;
import app.devir.stockquotemanager.exception.StockQuoteException;
import app.devir.stockquotemanager.exception.StockQuoteManagerException;
import app.devir.stockquotemanager.service.StockService;

@RestController
@RequestMapping("/stock")
public class StockQuoteController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private StockService service;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> create(@RequestBody String stockJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Stock stock = mapper.readValue(stockJson, Stock.class);

            StockQuoteManagerClient client = new StockQuoteManagerClient();
            client.verifyStock(stock.getId());

            service.save(stock);
        } catch (StockQuoteManagerException e) {
            LOGGER.error(e);
            return new ResponseEntity<>("Stock id not found.", HttpStatus.NOT_FOUND);

        } catch (StockQuoteException e) {
            LOGGER.error(e);
            return new ResponseEntity<>("Unexpected request.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            LOGGER.error(e);
            return new ResponseEntity<>("Unexpected error. Contact administrator.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>("Quote successfully added.", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StockEntity>> findAll() {
        List<StockEntity> stocks = new ArrayList<>();
        try {
            stocks = service.findAll();

            if (stocks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(stocks, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<StockEntity> findById(@PathVariable String id) {
        Optional<StockEntity> stock;
        try {
            stock = service.findById(id);

            if (!stock.isPresent()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            LOGGER.error(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(stock.get(), HttpStatus.OK);
    }

}
