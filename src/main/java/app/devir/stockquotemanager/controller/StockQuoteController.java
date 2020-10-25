package app.devir.stockquotemanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.devir.stockquotemanager.entity.StockEntity;
import app.devir.stockquotemanager.entity.dto.Stock;
import app.devir.stockquotemanager.exception.StockQuoteException;
import app.devir.stockquotemanager.service.StockService;

@RestController
@RequestMapping("/stock")
public class StockQuoteController {

    @Autowired
    private StockService service;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<String> create(@RequestBody String stockJson) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Stock stock = mapper.readValue(stockJson, Stock.class);
            service.save(stock);
        } catch (StockQuoteException e) {

            return new ResponseEntity<String>("Unexpected request.", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            
            return new ResponseEntity<String>("Unexpected error. Contact administrator", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        return new ResponseEntity<>("Quote(s) successfully added", HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<StockEntity>> findAll() {
        List<StockEntity> stocks = new ArrayList<>();
        try {
            stocks = service.findAll();

            if (stocks.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            
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
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(stock.get(), HttpStatus.OK);
    }

}
