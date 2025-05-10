package com.aluracursos.conversordemonedas.controllers;

import com.aluracursos.conversordemonedas.models.Currency;
import com.aluracursos.conversordemonedas.service.CurrencyConverterService;
import com.aluracursos.conversordemonedas.utils.ConsoleUtil;


import java.util.ArrayList;
import java.util.List;

public class ConsoleController {
    private final CurrencyController currencyController;
    private final CurrencyConverterService converterService;

    public ConsoleController(CurrencyController currencyController, CurrencyConverterService converterService) {
        this.currencyController = currencyController;
        this.converterService = converterService;
    }

    public void start() {
        ConsoleUtil.displayWelcomeMessage();

        try {
            boolean connected = currencyController.updateExchangeRates();
            if (!connected) {
                ConsoleUtil.displayErrorMessage("No se pudo conectar con la API de tasas de cambio.");
                return;
            }
            ConsoleUtil.displayConnectionSuccess();

            boolean running = true;
            while (running) {
                List<Currency> availableCurrencies = converterService.getAvailableCurrencies();
                int option = ConsoleUtil.displayMainMenu(availableCurrencies);

                if (option == 0) {
                    running = false;
                } else if (option > 0 && option <= availableCurrencies.size()) {
                    handleCurrencyConversion(availableCurrencies.get(option - 1));
                } else {
                    ConsoleUtil.displayErrorMessage("Opción inválida. Por favor, intente nuevamente.");
                }
            }

            ConsoleUtil.displayGoodbyeMessage();
        } catch (Exception e) {
            ConsoleUtil.displayErrorMessage("Error al iniciar la aplicación: " + e.getMessage());
        } finally {
            ConsoleUtil.closeScanner();
        }
    }

    private void handleCurrencyConversion(Currency sourceCurrency) {
        List<Currency> availableCurrencies = converterService.getAvailableCurrencies();
        List<Currency> targetCurrencies = new ArrayList<>();
        for (Currency currency : availableCurrencies) {
            if (!currency.getCode().equals(sourceCurrency.getCode())) {
                targetCurrencies.add(currency);
            }
        }

        int option = ConsoleUtil.displayTargetCurrencyMenu(availableCurrencies, sourceCurrency);

        if (option > 0 && option <= targetCurrencies.size()) {
            Currency targetCurrency = targetCurrencies.get(option - 1);

            double amount = ConsoleUtil.getAmountInput();
            if (amount <= 0) {
                ConsoleUtil.displayErrorMessage("Cantidad inválida. Por favor, ingrese un valor positivo.");
                return;
            }

            try {
                double result = currencyController.convertCurrency(
                        sourceCurrency.getCode(),
                        targetCurrency.getCode(),
                        amount
                );
                double rate = converterService.getExchangeRate(
                        sourceCurrency.getCode(),
                        targetCurrency.getCode()
                );
                ConsoleUtil.displayConversionResult(
                        sourceCurrency,
                        targetCurrency,
                        amount,
                        result,
                        rate
                );
            } catch (Exception e) {
                ConsoleUtil.displayErrorMessage("Error al realizar la conversión: " + e.getMessage());
            }
        } else if (option != 0) {
            ConsoleUtil.displayErrorMessage("Opción inválida. Por favor, intente nuevamente.");
        }
    }
}
