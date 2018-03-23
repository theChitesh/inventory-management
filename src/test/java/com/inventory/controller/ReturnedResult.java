package com.inventory.controller;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.inventory.services.Result;

public class ReturnedResult {
	
	private final Result result;
	
	@JsonCreator
	public ReturnedResult(Result result) {
		this.result = result;
	}

	public Result getResult() {
		return result;
	}

	
}
