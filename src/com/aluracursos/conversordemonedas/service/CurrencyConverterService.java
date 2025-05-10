package com.aluracursos.conversordemonedas.service;

import com.aluracursos.conversordemonedas.models.Currency;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CurrencyConverterService {
    private Map<String, Currency> currencies;

    public CurrencyConverterService(Map<String, Currency> currencies) {
        this.currencies = currencies;
    }

    public CurrencyConverterService() {

    }

    public double convert(String fromCurrency, String toCurrency, double amount) {
        if (!currencies.containsKey(fromCurrency) || !currencies.containsKey(toCurrency)) {
            throw new IllegalArgumentException("Moneda no disponible");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }

        double fromRate = currencies.get(fromCurrency).getRate();
        double toRate = currencies.get(toCurrency).getRate();

        double amountInUSD = amount / fromRate;
        return amountInUSD * toRate;
    }

    public List<Currency> getAvailableCurrencies() {
        return new ArrayList<>(currencies.values());
    }

    public Currency getCurrencyByCode(String code) {
        return currencies.get(code);
    }

    public double getExchangeRate(String fromCurrency, String toCurrency) {
        double fromRate = currencies.get(fromCurrency).getRate();
        double toRate = currencies.get(toCurrency).getRate();
        return toRate / fromRate;
    }
}
