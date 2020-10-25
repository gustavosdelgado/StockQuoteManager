package app.devir.stockquotemanager.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.naming.CommunicationException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import app.devir.stockquotemanager.exception.StockQuoteManagerException;

public class StockQuoteManagerClientTest {

    private static final String ID = "PETR4";
    private static final String BODY = "body";
    private static final String JSON = "[{\"id\": \"petr4\",\"description\": \"Petrobras PN\"},{\"id\": \"vale5\",\"description\": \"Vale do Rio Doce PN\"}]";

    @Mock
    private RestTemplate mockRestTemplate;
    @Mock
    private ObjectMapper mockObjectMapper;

    @InjectMocks
    private StockQuoteManagerClient client;

    @Test
    public void stockIdFound() throws JsonProcessingException, CommunicationException, StockQuoteManagerException {
        MockitoAnnotations.initMocks(this);

        ResponseEntity<String> response = new ResponseEntity<String>(JSON, HttpStatus.OK);
        when(mockRestTemplate.getForEntity("http://localhost:8080/stock", String.class)).thenReturn(response);

        StockQuoteManager stock = new StockQuoteManager();
        stock.setId(ID);
        List<StockQuoteManager> stocksListed = new ArrayList<>();
        stocksListed.add(stock);
        when(mockObjectMapper.readValue(BODY, new TypeReference<List<StockQuoteManager>>() {
        })).thenReturn(stocksListed);

        client.verifyStock(ID);
        assertEquals(true, client.isStockInList());
    }

    @Test
    public void stockIdNotFound() throws JsonProcessingException, CommunicationException, StockQuoteManagerException {
        MockitoAnnotations.initMocks(this);

        ResponseEntity<String> response = new ResponseEntity<String>(JSON, HttpStatus.OK);
        when(mockRestTemplate.getForEntity("http://localhost:8080/stock", String.class)).thenReturn(response);

        StockQuoteManager stock = new StockQuoteManager();
        stock.setId(ID);
        List<StockQuoteManager> stocksListed = new ArrayList<>();
        stocksListed.add(stock);
        when(mockObjectMapper.readValue(BODY, new TypeReference<List<StockQuoteManager>>() {
        })).thenReturn(stocksListed);

        Assertions.assertThrows(StockQuoteManagerException.class, () -> {
            client.verifyStock("id");
        });
    }

    @Test
    public void throwsUnexpectedException() throws JsonProcessingException, CommunicationException, StockQuoteManagerException {
        MockitoAnnotations.initMocks(this);

        when(mockRestTemplate.getForEntity("http://localhost:8080/stock", String.class)).thenThrow(new RuntimeException());

        Assertions.assertThrows(RuntimeException.class, () -> {
            client.verifyStock("id");
        });
    }
}
