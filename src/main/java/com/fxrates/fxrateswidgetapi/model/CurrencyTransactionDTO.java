package com.fxrates.fxrateswidgetapi.model;

import io.swagger.annotations.ApiModelProperty;

public class CurrencyTransactionDTO {

	private Long id;
	@ApiModelProperty(example = "PHP")
	String fromCurrency;
	@ApiModelProperty(example = "USD")
	String toCurrency;
	@ApiModelProperty(example = "200.00")
	Double amount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFromCurrency() {
		return fromCurrency;
	}

	public void setFromCurrency(String fromCurrency) {
		this.fromCurrency = fromCurrency;
	}

	public String getToCurrency() {
		return toCurrency;
	}

	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
