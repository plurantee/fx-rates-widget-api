package com.fxrates.fxrateswidgetapi.config;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortfolioConfiguration {

	@Value("${fx.wallet.amount}")
	private Double amount;
	@Value("${fx.wallet.currency}")
	private String currency;

	/**
	 * Set starting wallet
	 */
	@Bean
	public HashMap<String, Double> portfolio() {
		HashMap<String, Double> portfolio = new HashMap<String, Double>();
		portfolio.put(currency, amount);
		return portfolio;
	}
}
