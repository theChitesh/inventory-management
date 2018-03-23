package com.inventory.repository;

import com.inventory.domain.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * Interface which extends the Spring data repository to use the in build CRUD operations
 * @author chitesh
 *
 */
@Repository
public interface StockRepository extends CrudRepository<Stock, Integer> {
}
