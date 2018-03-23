package com.inventory.domain;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Domain class which stores attributes related to stock
 * @author chitesh
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Entity
public class Stock {

	@Id	
	@GeneratedValue
	private Integer id;

	@NotBlank(message = "stock name can not be empty!")
	@Column(unique=true)
	private String name;
	
	
	@NotNull(message  ="quantity can not be null")
	private Integer quantity;


	private Boolean activatePromotion;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "UTC")
	private LocalDate entryDate;
	
	private String entryBy;

	@JsonFormat
	private LocalDate lastUpdateDate;

	private String updatedBy;

	@NotNull(message  ="Per day cost of stock can not be empty.")
	private Integer amountPerDay;
	
	private Boolean orderAdditionalStock;
	
	private Integer daysInInventory;
	
	private Integer inventoryCost;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

		

	public LocalDate getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(LocalDate entryDate) {
		this.entryDate = entryDate;
	}

	public String getEntryBy() {
		return entryBy;
	}

	public void setEntryBy(String entryBy) {
		this.entryBy = entryBy;
	}

	public LocalDate getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(LocalDate lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Boolean getActivatePromotion() {
		return activatePromotion;
	}

	public void setActivatePromotion(Boolean activatePromotion) {
		
		this.activatePromotion = activatePromotion;
	}

	public Integer getAmountPerDay() {
		return amountPerDay;
	}

	public void setAmountPerDay(Integer amountPerDay) {
		this.amountPerDay = amountPerDay;
	}

	public Boolean getOrderAdditionalStock() {
		return orderAdditionalStock;
	}

	public void setOrderAdditionalStock(Boolean orderAdditionalStock) {
		this.orderAdditionalStock = orderAdditionalStock;
	}

	public Integer getDaysInInventory() {
		return daysInInventory;
	}

	public void setDaysInInventory(Integer daysInInventory) {
		this.daysInInventory = daysInInventory;
	}

	public Integer getInventoryCost() {
		return inventoryCost;
	}

	public void setInventoryCost(Integer inventoryCost) {
		this.inventoryCost = inventoryCost;
	}

	
}
