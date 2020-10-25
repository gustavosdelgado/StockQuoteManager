package app.devir.stockquotemanager.entity.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stock {

    @JsonProperty("id")
    private String id;

    @JsonProperty("quotes")
    private Map<String, Object> quotes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getQuotes() {
        return quotes;
    }

    public void setQuotes(Map<String, Object> quotes) {
        this.quotes = quotes;
    }
    
}
