package com.fxrates.fxrateswidgetapi.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;

import com.fxrates.fxrateswidgetapi.model.ExchangeRatesApiResponse;
import com.google.gson.Gson;

public class ExchangeRateUtil {

	@Value("${fx.exchangerateapi.key}")
	private static String apiKey;

	private ExchangeRateUtil() {
	}

	public static HashMap<String, Double> addToPortfolio(String currency, Double amount,
			HashMap<String, Double> portfolio) {
		Double currentValue = portfolio.get(currency);
		if (currentValue != null) {
			portfolio.put(currency, currentValue + amount);
		} else {
			portfolio.put(currency, amount);
		}
		return portfolio;
	}

	public static HashMap<String, Double> minus(String currency, Double amount, HashMap<String, Double> portfolio) {
		Double currentValue = portfolio.get(currency);
		if (currentValue == null) {
			// Invalid Transaction
			return portfolio;
		}
		portfolio.put(currency, currentValue - amount);
		return portfolio;
	}

	public static ExchangeRatesApiResponse exchangeRateApiCall(String from, String to) {
		String urlStri = "http://api.exchangeratesapi.io/v1/latest?access_key=" + apiKey + "&symbols=" + from + ","
				+ to;

		try {
			URL url = new URL(urlStri);
			HttpURLConnection request = (HttpURLConnection) url.openConnection();
			request.setRequestMethod("GET");

			request.connect();

			int status = request.getResponseCode();
			switch (status) {
			case 200:
			case 201:
				BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) {
					sb.append(line + "\n");
				}
				br.close();
				Gson gson = new Gson();
				return gson.fromJson(sb.toString(), ExchangeRatesApiResponse.class);
			default:
				return null;
			}
		} catch (IOException e) {
			return null;
		}
	}
}
