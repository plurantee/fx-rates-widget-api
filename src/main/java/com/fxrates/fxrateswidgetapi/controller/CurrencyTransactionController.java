package com.fxrates.fxrateswidgetapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fxrates.fxrateswidgetapi.model.CurrencyTransaction;
import com.fxrates.fxrateswidgetapi.model.CurrencyTransactionDTO;
import com.fxrates.fxrateswidgetapi.service.CurrencyTransactionService;

@RestController
@RequestMapping("api/currencytransaction")
public class CurrencyTransactionController {

	@Autowired
	CurrencyTransactionService currencyTransactionService;

	@GetMapping
	public List<CurrencyTransaction> getAll() {
		return currencyTransactionService.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<CurrencyTransaction> getCurrencyTransaction(@PathVariable Long id) {
		return currencyTransactionService.getCurrencyTransaction(id);
	}

	@PostMapping()
	public ResponseEntity<CurrencyTransaction> addCurrencyTransaction(
			@RequestBody CurrencyTransactionDTO currencyTransactionDto) {
		return currencyTransactionService.addCurrencyTransaction(currencyTransactionDto);
	}

}
