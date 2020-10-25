package app.devir.stockquotemanager.repository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.devir.stockquotemanager.entity.StockEntity;

@Repository
public interface StockRepository extends JpaRepository<StockEntity, String> {
    
    @Override
    @CacheEvict("stockByIdentifier")
    <S extends StockEntity> S save(S stock);
    
    @Override
    @CacheEvict("stockByIdentifier")
    void delete(StockEntity stock);

}
