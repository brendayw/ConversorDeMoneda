package com.aluracursos.conversordemonedas.controllers;

import com.aluracursos.conversordemonedas.service.CurrencyConverterService;
import com.aluracursos.conversordemonedas.service.ExchangeRateService;
import com.aluracursos.conversordemonedas.models.Currency;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CurrencyController {
    private final ExchangeRateService exchangeRateService;
    private CurrencyConverterService converterService;

    public CurrencyController() {
        this.exchangeRateService = new ExchangeRateService();
        this.converterService = new CurrencyConverterService();
        updateExchangeRates();
    }

    public boolean updateExchangeRates() {
        try {
            CompletableFuture<Map<String, Currency>> futureRates = exchangeRateService.fetchExchangeRatesAsync();
            Map<String, Currency> currencies = futureRates.get(10, TimeUnit.SECONDS);

            if (currencies == null || currencies.isEmpty()) {
                return false;
            }

            this.converterService = new CurrencyConverterService(currencies);
            return true;
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            if (this.converterService == null) {
                this.converterService = new CurrencyConverterService();
            }
            return false;
        }
    }

    public double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        if (converterService == null) {
            throw new IllegalStateException("El servicio de " +
                    "conversión no está inicializado.");
        }
        return converterService.convert(fromCurrency, toCurrency, amount);
    }

    public CurrencyConverterService getConverterService() {
        return converterService;
    }
}