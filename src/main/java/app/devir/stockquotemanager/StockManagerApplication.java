package app.devir.stockquotemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class StockManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockManagerApplication.class, args);
	}

}