package com.fxrates.fxrateswidgetapi.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fxrates.fxrateswidgetapi.model.CurrencyTransaction;
import com.fxrates.fxrateswidgetapi.model.CurrencyTransactionDTO;
import com.fxrates.fxrateswidgetapi.model.ExchangeRatesApiResponse;
import com.fxrates.fxrateswidgetapi.model.repository.CurrencyTransactionRepository;
import com.fxrates.fxrateswidgetapi.util.ExchangeRateUtil;

@Service
public class CurrencyTransactionService {

	private CurrencyTransactionRepository currencyTransactionRepository;

	private ModelMapper modelMapper;

	private HashMap<String, Double> portfolio;

	public CurrencyTransactionService(CurrencyTransactionRepository currencyTransactionRepository,
			ModelMapper modelMapper, HashMap<String, Double> portfolio) {
		this.currencyTransactionRepository = currencyTransactionRepository;
		this.modelMapper = modelMapper;
		this.portfolio = portfolio;
	}

	public List<CurrencyTransaction> getAll() {
		return currencyTransactionRepository.findAll();
	}

	public ResponseEntity<CurrencyTransaction> getCurrencyTransaction(Long id) {
		Optional<CurrencyTransaction> currencyTransaction = currencyTransactionRepository.findById(id);
		if (currencyTransaction.get() != null) {
			return ResponseEntity.ok().body(currencyTransaction.get());
		}
		return ResponseEntity.badRequest().body(null);
	}

	public ResponseEntity<CurrencyTransaction> addCurrencyTransaction(CurrencyTransactionDTO currencyTransactionDto) {
		CurrencyTransaction result = saveTransaction(currencyTransactionDto);
		if (result != null) {
			currencyTransactionRepository.save(result);
			return ResponseEntity.ok().body(result);
		}
		return ResponseEntity.badRequest().body(null);
	}

	public ResponseEntity<CurrencyTransaction> updateCurrencyTransaction(CurrencyTransaction currencyTransaction) {
		if (currencyTransaction.getId() != null) {
			return ResponseEntity.badRequest().body(null);
		}
		CurrencyTransaction result = currencyTransactionRepository.save(currencyTransaction);

		return ResponseEntity.ok().body(result);
	}

	public void deleteCurrencyTransaction(Long id) {
		currencyTransactionRepository.deleteById(id);
	}

	private CurrencyTransaction saveTransaction(CurrencyTransactionDTO currencyTransactionDto) {
		Double currentValue = portfolio.get(currencyTransactionDto.getFromCurrency());

		// Checks if user has the current currency and if current amount is sufficient
		if (currentValue == null || currentValue < currencyTransactionDto.getAmount()) {
			return null;
		}
		CurrencyTransaction currencyTransaction = modelMapper.map(currencyTransactionDto, CurrencyTransaction.class);
		currencyTransaction.setTransactionDate(LocalDate.now());
		ExchangeRatesApiResponse exchangeRateApiResponse = ExchangeRateUtil.exchangeRateApiCall(currencyTransaction.getFromCurrency(), currencyTransaction.getToCurrency());

		if (exchangeRateApiResponse == null) {
			return null;
		}
		ExchangeRateUtil.minus(currencyTransaction.getFromCurrency(), currencyTransaction.getAmount(), portfolio);

		Double newCurrency = (currencyTransaction.getAmount()
				/ exchangeRateApiResponse.getRates().get(currencyTransaction.getFromCurrency()))
				* exchangeRateApiResponse.getRates().get(currencyTransaction.getToCurrency());

		ExchangeRateUtil.addToPortfolio(currencyTransaction.getToCurrency(), newCurrency, portfolio);

		currencyTransaction.setResultantAmount(newCurrency);
		return currencyTransaction;
	}

}
