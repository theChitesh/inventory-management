package com.inventory.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.springframework.test.web.servlet.ResultActions;
import com.inventory.BaseTest;


public class InventoryManagementControllerTest extends BaseTest {
	
	
	
  @Test
  public void getStocks_WithoutElement_ShouldReturnOk() throws Exception {
    getAllStocks(ADMIN_TOKEN).andExpect(status().isOk());
  }

  @Test
  public void getStocks_WithoutElement_ShouldReturnEmptyList() throws Exception {
    final ResultActions allStocks = getAllStocks(ADMIN_TOKEN);
    allStocks.andExpect(status().isOk());
    final Stocks stocks = extractDtoFromMockResult(allStocks.andReturn(), Stocks.class);
    assertFalse(stocks.getStocks().isEmpty());
  }
  
  @Test
  public void testAddStock_shouldReturnID() throws Exception {
	  
	  final ResultActions response = addStock(ADMIN_TOKEN,stock) ;
	  response.andExpect(status().isCreated());
	  final ReturnedResult retResult = extractDtoFromMockResult(response.andReturn(), ReturnedResult.class);
	  assertEquals(new Integer(1), retResult.getResult().getId());

  }
  
  @Test
  public void testGetStockAfterAddition() throws Exception {
	  
	  final ResultActions allStocks = getAllStocks(ADMIN_TOKEN);
	   allStocks.andExpect(status().isOk());
	   final Stocks stocks = extractDtoFromMockResult(allStocks.andReturn(), Stocks.class);
	   assertEquals(1, stocks.getStocks().size());
  }
  
  @Test
  public void testUpdateStockAfterAddition() throws Exception {
	  stock.setId(1);
	  stock.setAmountPerDay(10);
	  final ResultActions result = updateStock(ADMIN_TOKEN, stock, "1");
	  result.andExpect(status().isNoContent());
  }
  
  
}