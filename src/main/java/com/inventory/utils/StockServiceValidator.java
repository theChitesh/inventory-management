package com.inventory.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.inventory.domain.Stock;
import com.inventory.exceptions.StockException;

/**
 * Class used to validate the request data for further processing. 
 * @author chitesh
 *
 */
@Service
public class StockServiceValidator {

	/**
	 * Method used to verify the integer value of the ID.
	 * @param id - String input parameter
	 * @return integer value of ID
	 * @throws StockException - throws 
	 */
	public int validateId(final String id) throws StockException {
		
		if(!StringUtils.isNumeric(id)) {
			throw new StockException("Id can not be alphanumeric");
		}
		
		int parsedId = Integer.parseInt(id);
		
		return parsedId;
	}
	
	/**
	 * Method helps in validating the ID present in Path Param with the ID attached to the resource body.
	 * @param id - ID present in the path param
	 * @param stock - Stock details
	 * @throws StockException
	 */
	public void validateIdinParamAndPayload(int id , Stock stock) throws StockException{
	
		if((null != stock.getId()) && (id != stock.getId())) {
			throw new StockException("Request body has a different ID than ID present in rquest URL.");
		}
	}
	
	/**
	 * Method is used to if the stock with the same name is present in database or not.
	 * @param stocks
	 * @param newStock
	 */
	/*public void checkIfStockIsPresent(List<Stock> stocks , Stock newStock) {
		
		boolean flag = stocks.stream().anyMatch(stock -> stock.getName().equals(newStock.getName()));
		if(flag) {
			throw new StockException("Stock with same name is already present.");
		}
	}*/
	
	/**
	 * Method used to validate the resource before updating in database.
	 * @param availableStock - the available stock in database.
	 * @param newStock - stock with updated attributes
	 * @throws StockException
	 */
	public void validateForUpdate(Stock availableStock , Stock newStock) throws StockException {
		
		if(null == availableStock) {
			throw new StockException("No stock is available for update");
		}
		
		if(!availableStock.getName().equals(newStock.getName())) {
			throw new StockException("Stock Name can not be changed !");
		}
	}
}
