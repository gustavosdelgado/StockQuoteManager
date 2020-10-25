package app.devir.stockquotemanager.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity(name = "stock")
@JsonInclude(content = Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class StockEntity {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("quotes")
    @ElementCollection
	private Map<LocalDate, BigDecimal> quotes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<LocalDate, BigDecimal> getQuotes() {
        return quotes;
    }

    public void setQuotes(Map<LocalDate, BigDecimal> quotes) {
        this.quotes = quotes;
    }

}
