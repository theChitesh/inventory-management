package com.inventory.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.domain.Stock;
import com.inventory.exceptions.StockException;
import com.inventory.services.Result;
import com.inventory.services.StockServices;
import com.inventory.utils.StockServiceValidator;

/**
 * Rest resource class to deal with the REST operation on the stock resource.
 * This class will respond to the request with valid Authorization JWT token.
 * Two roles are defined to access the resource. 
 * 1) Admin Role : "sys-admin"
 * 2) User Role  : "user"
 * @author chitesh
 *
 */
@RestController
@RequestMapping("/stocks")
public class InventoryManagementController {


	private final StockServices stockServices;
	

	private final StockServiceValidator serviceValidator;

	@Autowired
	public InventoryManagementController(final StockServices stockServices,
			final StockServiceValidator serviceValidator) {
		this.stockServices = stockServices;
		this.serviceValidator = serviceValidator;
	}
	
	/**
	 * Method will return the list of stocks available in Inventory.
	 * @return
	 */
	@GetMapping
	@PreAuthorize("hasRole('user') or hasRole('sys-admin')")
	public List<Stock> getAllItems() {
		return stockServices.getAllStocks();
	}
	
	/**
	 * Method will return the details of particular stock, for which the request is made.
	 * @param id - Id of the stock.
	 * @return Stock details
	 */
	@PreAuthorize("hasRole('user') or hasRole('sys-admin')")
	@RequestMapping(method = RequestMethod.GET , value = "/{id}")
	public Stock getSelectedItems(@PathVariable("id") final String id) {

		int parsedId = serviceValidator.validateId(id);
		return stockServices.getSelectedItem(parsedId);
	}

	/**
	 * Method helps in adding a new resource to the inventory.
	 * Only user with sys-admin role can perform this operation.
	 * 
	 * @param stock - stock details to be added
	 * @return - stock id.
	 */
	@PostMapping
	@PreAuthorize("hasRole('sys-admin')")
	public ResponseEntity addStock(@RequestBody @Valid Stock stock, HttpServletRequest request) {
		
		String userName = request.getUserPrincipal().getName();
		stock.setEntryBy(userName);
		Result result = new Result();
		int id = stockServices.addStock(stock);
		result.setId(id);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	/**
	 * Method is used to update the details of a particular stock.
	 * @param id - ID, of the stock to be updated.
	 * @param stock - stock details which needs to be updated.
	 * @return - no-content.
	 * @throws StockException
	 */
	@PreAuthorize("hasRole('sys-admin')")
	@RequestMapping(method = RequestMethod.POST, value = "/{id}")
	public ResponseEntity updateStock(@PathVariable("id") final String id, @RequestBody Stock stock,
			HttpServletRequest request) throws StockException {
		
		int parsedId = serviceValidator.validateId(id);
		serviceValidator.validateIdinParamAndPayload(parsedId, stock);
		
		
		
		String userName = request.getUserPrincipal().getName();
		stock.setUpdatedBy(userName);
		
		stockServices.updateStock(parsedId, stock);
		return ResponseEntity.noContent().build();
	}

	}
