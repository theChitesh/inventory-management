package com.inventory.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import com.inventory.domain.Stock;

/**
 * Test cases for Business Rules class
 * @author chitesh
 *
 */

@RunWith(MockitoJUnitRunner.class)
public class BusinessRulesTest {
	
	@InjectMocks
	private BusinessRules bRules;
	
	private Stock stock;
	
	@Before
	public void setUp() {
		
		stock = new Stock();
		
		stock.setName("First Item");
		LocalDate pastDate = LocalDate.of(2017, 9, 25);
		stock.setEntryDate(pastDate);
		
		stock.setQuantity(1000);
		stock.setAmountPerDay(100);
	}
	
	@Test
	public void testForDurationOfStock() {
		
		LocalDate today = LocalDate.now();
		Integer duration = Period.between(stock.getEntryDate(), today).getDays();
		Stock updatedStock = bRules.enrichStockParameters(stock);
		Assert.assertEquals(duration, updatedStock.getDaysInInventory());

	}
	
	@Test
	public void testPromotionNotSet() {
		LocalDate pastDate = LocalDate.of(2017, 9, 25);
		stock.setEntryDate(pastDate);
		Stock updatedStock = bRules.enrichStockParameters(stock);
		Assert.assertFalse(updatedStock.getActivatePromotion());
	}
	
	@Test
	public void testPromotionNotSetSecond() {
		stock.setEntryDate(LocalDate.now());
		stock.setQuantity(10);
		Stock updatedStock = bRules.enrichStockParameters(stock);
		Assert.assertFalse(updatedStock.getActivatePromotion());
	}
	
	@Test
	public void testPromotionSetForStock() {

		LocalDate pastDate = LocalDate.of(2016, 9, 25);

		stock.setEntryDate(pastDate);
		Stock updatedStock = bRules.enrichStockParameters(stock);
		Assert.assertTrue(updatedStock.getActivatePromotion());
	}
	
	@Test
	public void testAdditionalQuantityOrderFlagIsSet() {
		stock.setQuantity(10);
		Stock updateStock = bRules.enrichStockParameters(stock);
		Assert.assertTrue(updateStock.getOrderAdditionalStock());
	}
	
	
	@Test
	public void testInventoryCostSet() {
		int amountPerDay = 10;
		
		LocalDate entryDate = LocalDate.of(2017, 9, 20);
		
		int duartionIndays = (int) ChronoUnit.DAYS.between(entryDate, LocalDate.now());
		
		stock.setEntryDate(entryDate);
		Integer estimatedCost = amountPerDay * duartionIndays;
		stock.setAmountPerDay(amountPerDay);
		
		Stock updateStock = bRules.enrichStockParameters(stock);
		Assert.assertEquals(estimatedCost, updateStock.getInventoryCost());
		
		
	}

}
