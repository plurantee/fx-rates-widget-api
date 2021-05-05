package com.fxrates.fxrateswidgetapi.model;

import java.util.HashMap;

public class ExchangeRatesApiResponse {

	private String base;
	HashMap<String, Double> rates;

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public HashMap<String, Double> getRates() {
		return rates;
	}

	public void setRates(HashMap<String, Double> rates) {
		this.rates = rates;
	}

}
