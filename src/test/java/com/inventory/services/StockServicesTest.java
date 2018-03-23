package com.inventory.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.inventory.domain.Stock;
import com.inventory.exceptions.StockException;
import com.inventory.repository.StockRepository;
import com.inventory.utils.StockServiceValidator;

@RunWith(MockitoJUnitRunner.class)
public class StockServicesTest {
	
	
	@InjectMocks
	private StockServices stockServices;
	
	@Mock
	private StockRepository stockRepository;
	
	@Mock
	private BusinessRules businessRules;
	
	@InjectMocks
	private Stock stock;
	
	@Mock
	private StockServiceValidator stockValidator;
	
	
	
	@Test
	public void testAddStock() {
		stock.setName("Test");
		stock.setId(1);
		
		Stock toSave = new Stock();
		
		Mockito.when(stockRepository.save(toSave)).thenReturn(stock);
		Integer id = stockServices.addStock(toSave);
		Assert.assertEquals(id, stock.getId());

	}
	
	@Test
	public void testGetAllStocks() {
		
		Stock stock1 = new Stock();
		stock1.setId(1);
		stock1.setName("First");
		stock1.setAmountPerDay(1);
		
		
		
		Stock stock2 = new Stock();
		stock2.setId(2);
		stock2.setName("Second");
		stock2.setAmountPerDay(1);
		
		List<Stock> stocks = new ArrayList<>();
		stocks.add(stock1); stocks.add(stock2);
		
		Mockito.when(stockRepository.findAll()).thenReturn(stocks);
		
		List<Stock> outputStock = stockServices.getAllStocks();
		Assert.assertNotNull(outputStock);
		Assert.assertEquals(stocks.size(), outputStock.size());
		
		
	}
	
	@Test
	public void testGetSelectedItem() {
		
		stock.setId(1);
		stock.setName("First");
		
		Mockito.when(stockRepository.findOne(1)).thenReturn(stock);
		Stock  outputStock = stockServices.getSelectedItem(1);
		Assert.assertEquals(stock.getName()	, outputStock.getName());
	}

	@Test
	public void testUpdateStock() {
		
		stock.setId(1);
		stock.setName("First");
		
		Stock newStock = new Stock();
		
		newStock.setId(1);
		newStock.setName("Second");
		
		try {
		Mockito.when(stockRepository.findOne(1)).thenReturn(stock);
		Mockito.doThrow(new StockException("sfssdfsfsd")).when(stockValidator).validateForUpdate(stock, newStock);
		
		stockServices.updateStock(1, stock);
		}catch(StockException excption) {
			System.out.println("in catch");
			
		}
		
	}
	
}
