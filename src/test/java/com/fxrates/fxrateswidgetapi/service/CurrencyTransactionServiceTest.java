package com.fxrates.fxrateswidgetapi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.util.HashMap;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.test.util.ReflectionTestUtils;

import com.fxrates.fxrateswidgetapi.model.CurrencyTransaction;
import com.fxrates.fxrateswidgetapi.model.CurrencyTransactionDTO;
import com.fxrates.fxrateswidgetapi.model.ExchangeRatesApiResponse;
import com.fxrates.fxrateswidgetapi.model.repository.CurrencyTransactionRepository;
import com.fxrates.fxrateswidgetapi.util.ExchangeRateUtil;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CurrencyTransactionServiceTest {

	@Mock
	CurrencyTransactionRepository currencyTransactionRepository;
	@Mock
	ModelMapper modelMapper;
	

	@InjectMocks
	CurrencyTransactionService currencyTransactionService;

	private CurrencyTransactionDTO currencyTransactionDto;

	private CurrencyTransaction currencyTransaction;

	private ExchangeRatesApiResponse exchangeRatesApiResponse;

	private HashMap<String, Double> portfolio;

	@BeforeEach()
	void setUp() {
		MockitoAnnotations.openMocks(this);

		currencyTransactionDto = new CurrencyTransactionDTO();
		currencyTransactionDto.setId(1L);
		currencyTransactionDto.setAmount(100.0);
		currencyTransactionDto.setFromCurrency("PHP");
		currencyTransactionDto.setToCurrency("USD");

		currencyTransaction = new CurrencyTransaction();
		currencyTransaction.setId(1L);
		currencyTransaction.setAmount(100.0);
		currencyTransaction.setFromCurrency("PHP");
		currencyTransaction.setToCurrency("USD");

		portfolio = new HashMap<String, Double>();
		portfolio.put("PHP", 100000.00);
		// Based on current rate as of 10:55 AM PHT 05/05/2021
		exchangeRatesApiResponse = new ExchangeRatesApiResponse();
		exchangeRatesApiResponse.setBase("EUR");
		HashMap<String, Double> rates = new HashMap<String, Double>();
		rates.put("USD", 1.202066);
		rates.put("PHP", 57.663721);
		exchangeRatesApiResponse.setRates(rates);
	}

	@Test
	void testGetCurrencyTransaction() {
		when(currencyTransactionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(currencyTransaction));

		CurrencyTransaction currencyTransaction = currencyTransactionService.getCurrencyTransaction(1L).getBody();

		verify(currencyTransactionRepository, times(1)).findById(1L);

		assertEquals(currencyTransaction, this.currencyTransaction);
	};

	@Test
	void testAddCurrencyTransaction() {
		
		ReflectionTestUtils.setField(currencyTransactionService, "portfolio", portfolio);
		when(modelMapper.map(currencyTransactionDto, CurrencyTransaction.class)).thenReturn(currencyTransaction);
		
		CurrencyTransaction currencyTransaction = null;
		try (MockedStatic<ExchangeRateUtil> theMock = Mockito.mockStatic(ExchangeRateUtil.class)) {
            theMock.when(() -> ExchangeRateUtil.exchangeRateApiCall(any(),any()))
                   .thenReturn(exchangeRatesApiResponse);
            currencyTransaction = currencyTransactionService.addCurrencyTransaction(currencyTransactionDto).getBody();
        }
		assertNotNull(currencyTransaction);
		assertNotNull(currencyTransaction.getResultantAmount());
		
		// (100.0 / 57.663721) x 1.202066 = 2.084613998461875
		assertEquals(Double.valueOf(currencyTransaction.getResultantAmount()), Double.valueOf(2.084613998461875));
	}
}
