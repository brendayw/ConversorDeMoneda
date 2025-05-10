package com.aluracursos.conversordemonedas.service;

import com.aluracursos.conversordemonedas.models.Currency;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ExchangeRateService {
    private static final String API_KEY = "de059e046c591726660fe540";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private final HttpClient httpClient;

    public ExchangeRateService() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public Map<String, Currency> fetchExchangeRates() throws Exception {
        String apiUrl = BASE_URL + API_KEY + "/latest/USD";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Error al obtener tasas de cambio. Código de estado: " + response.statusCode());
        }
        return parseExchangeRates(response.body());
    }

    public CompletableFuture<Map<String, Currency>> fetchExchangeRatesAsync() {
        String apiUrl = BASE_URL + API_KEY + "/latest/USD";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(apiUrl))
                .GET()
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(response -> {
                    if (response.statusCode() != 200) {
                        throw new RuntimeException("Error al obtener tasas de cambio. Código de estado: "
                                + response.statusCode());
                    }
                    try {
                        return parseExchangeRates(response.body());
                    } catch (Exception e) {
                        throw new RuntimeException("Error al procesar la respuesta de la API", e);
                    }
                });
    }

    private Map<String, Currency> parseExchangeRates(String jsonResponse) throws Exception {
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        String result = jsonObject.get("result").getAsString();
        if (!"success".equals(result)) {
            throw new RuntimeException("Error en la respuesta de la API: " + result);
        }

        JsonObject conversionRates = jsonObject.get("conversion_rates").getAsJsonObject();

        Map<String, Currency> currencies = new HashMap<>();
        addCurrency(currencies, conversionRates, "ARS", "Peso argentino");
        addCurrency(currencies, conversionRates, "BOB", "Boliviano boliviano");
        addCurrency(currencies, conversionRates, "BRL", "Real brasileño");
        addCurrency(currencies, conversionRates, "CLP", "Peso chileno");
        addCurrency(currencies, conversionRates, "COP", "Peso colombiano");
        addCurrency(currencies, conversionRates, "USD", "Dólar estadounidense");

        return currencies;
    }

    private void addCurrency(Map<String, Currency> currencies, JsonObject rates, String code, String name) {
        if (rates.has(code)) {
            double rate = rates.get(code).getAsDouble();
            currencies.put(code, new Currency(code, name, rate));
        }
    }
}
