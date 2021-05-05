package com.fxrates.fxrateswidgetapi.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fxrates.fxrateswidgetapi.util.ExchangeRateUtil;

@RestController
@RequestMapping("api/wallet")
public class PortfolioController {

	@Autowired
	private HashMap<String, Double> portfolio;

	@GetMapping()
	public HashMap<String, Double> getPortfolio() {
		return portfolio;
	}

	@GetMapping("/{currency}/{amount}")
	public HashMap<String, Double> addAmount(@PathVariable String currency, @PathVariable Double amount) {
		return ExchangeRateUtil.addToPortfolio(currency, amount, portfolio);
	}
}
