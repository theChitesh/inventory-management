package com.inventory.controller;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.inventory.domain.Stock;

public class Stocks {

  private final List<Stock> stocks;

  @JsonCreator
  public Stocks(final List<Stock> stocks) {
    this.stocks = stocks;
  }

  public List<Stock> getStocks() {
    return stocks;
  }
}
