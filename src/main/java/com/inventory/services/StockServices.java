package com.inventory.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.inventory.domain.Stock;
import com.inventory.exceptions.StockException;
import com.inventory.repository.StockRepository;
import com.inventory.utils.StockServiceValidator;

/**
 * Stock Service class used to validate the operate the CRUD operation on the resources
 * @author chitesh
 *
 */
@Service
@ComponentScan("com.inventory.*")
public class StockServices {

	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private StockServiceValidator stockValidator;
	
	@Autowired
	private BusinessRules businessRules;
	
	/**
	 * Method helps in retrieving all 
	 * @return
	 */
	public List<Stock> getAllStocks() {
		List<Stock> stocks = new ArrayList<>();
//		stockRepository.findAll().forEach(stocks::add);
		
		Iterator<Stock> it = stockRepository.findAll().iterator();
		while(it.hasNext()) {
			Stock stock = businessRules.enrichStockParameters(it.next());
			stocks.add(stock);
		}
		return stocks;
	}
	
	/**
	 * Method helps in fetching the selected resource.
	 * @param id - ID of the Resource
	 * @return - Stock DTO
	 */
	public Stock getSelectedItem(final int id) {
		return stockRepository.findOne(id);
	}
	
	/**
	 * Method helps in saving the stock in database.
	 * @param stock - 
	 * @return
	 */
	public int addStock(Stock stock) {

		Stock stk = null;
		try {
			stock.setLastUpdateDate(null);
			stock.setUpdatedBy(null);
			stk = stockRepository.save(stock);
		} catch (DataIntegrityViolationException dexception) {

			throw new StockException("Stock with the same name is already present");
		}
		return stk.getId();
	}
	
	/**
	 * Method helps in updating the exisiting stock in database.
	 * @param id - Id of the Stock to be updated.
	 * @param stock - Stock details.
	 * @throws StockException 
	 */
	public void updateStock(final int id, Stock stock) throws StockException {
		
		Stock availableStock = getSelectedItem(id);
		
		stockValidator.validateForUpdate(availableStock, stock);
		stock.setLastUpdateDate(LocalDate.now());
		stockRepository.save(stock);
		
	}
}
