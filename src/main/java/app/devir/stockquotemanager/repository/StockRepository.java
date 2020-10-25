package app.devir.stockquotemanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.devir.stockquotemanager.entity.StockEntity;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, String> {
    
}
