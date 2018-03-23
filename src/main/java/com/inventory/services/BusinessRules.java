package com.inventory.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

import org.springframework.stereotype.Service;

import com.inventory.domain.Stock;
import com.inventory.utils.Constants;

/**
 * Class used to calculate the Business rules for the each of the stock items in Inventory.
 * 
 * Assumption is made for the following:-
 * 1) Promotion is active on any stock if it stays in inventory for more than 10 days and quantity is over 100 Units.
 * 2) Additional Quantity indicator is set if quantity of stock is less than 100 Units.
 * 
 * @author chitesh
 *
 */
@Service
public class BusinessRules {

	/**
	 * Method updates the stock parameters with different business rules
	 * @param stock - stock from DB.
	 * @return updated stock parameters.
	 */
	public Stock enrichStockParameters(Stock stock) {
		
		int durationInDays = getStockDurationInInventory(stock.getEntryDate());
		
		boolean promotionApplicable = isPromotionApplicable(durationInDays,stock.getQuantity());
		
		boolean additionalStock = indicateForAdditionalOrder(stock);
		int costInInventory = calculateInventoryCost(durationInDays , stock.getAmountPerDay());
		
		stock.setDaysInInventory(durationInDays);
		stock.setActivatePromotion(promotionApplicable);
		stock.setOrderAdditionalStock(additionalStock);
		stock.setInventoryCost(costInInventory);
		
		
		
		return stock;
	}
	
	/**
	 * Method used to calculate the duration of stock in inventory
	 * @param entryDate - of stock in inventory
	 * @return - duration
	 */
	private int getStockDurationInInventory(final LocalDate entryDate) {

		LocalDate today = LocalDate.now();
		int duartionIndays = (int) ChronoUnit.DAYS.between(entryDate, today);
		return duartionIndays;
	}
	
	/**
	 * Method applies a promotion on stock if it has quantity more tha 
	 * 100 Units and stays in inventory for more than 10 days.
	 * @param duration  - of stock in inventory
	 * @param quantity - of stock
	 * @return - boolean
	 */
	private boolean  isPromotionApplicable(final int duration, final int quantity) {
		
		if(duration >= Constants.ACTIVATE_PROMOTION_AFTER_TEN_DAYS && 
				quantity > Constants.HUNDRED_UNITS_OF_STOCKS ) {
			return true;
		}
		return false;
		
	}
	
	/**
	 * Method returns the indicator for ordering new stock for particular item.
	 * @param stock - 
	 * @return - boolean
	 */
	private boolean indicateForAdditionalOrder(Stock stock) {
		
		if(stock.getQuantity() < Constants.HUNDRED_UNITS_OF_STOCKS) {
			return true;
		}
		return false;
	}
	
	/**
	 * Method calculates the cost of keeping the stock in the inventory.
	 * @param amountPerday - amount per day
	 * @param duration - total duration of stock.
	 * @return - cost
	 */
	private int calculateInventoryCost(final int amountPerday, final int duration ) {
		int cost = amountPerday * duration;
		return cost;
	}
	
}
