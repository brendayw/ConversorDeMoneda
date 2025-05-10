package com.aluracursos.conversordemonedas.utils;

import com.aluracursos.conversordemonedas.models.Currency;
import java.util.List;
import java.util.Scanner;

public class ConsoleUtil {
    private static final Scanner scanner = new Scanner(System.in);

    public static void displayWelcomeMessage() {
        System.out.println("==============================================");
        System.out.println("    ¡Bienvenido/a al Conversor de Monedas!    ");
        System.out.println("==============================================");
        System.out.println("Conectando con la API de tasas de cambio...");
    }

    public static void displayConnectionSuccess() {
        System.out.println("¡Conexión exitosa! Tasas de cambio actualizadas.");
    }

    public static void displayErrorMessage(String message) {
        System.out.println("ERROR: " + message);
    }

    public static void displayInfoMessage(String message) {
        System.out.println(message);
    }

    public static int displayMainMenu(List<Currency> availableCurrencies) {
        System.out.println("\n==============================================");
        System.out.println("              MENÚ PRINCIPAL                 ");
        System.out.println("==============================================");
        System.out.println("Elija la moneda de origen:");

        for (int i = 0; i < availableCurrencies.size(); i++) {
            System.out.println((i + 1) + ". " + availableCurrencies.get(i));
        }
        System.out.println("0. Salir");
        System.out.print("Ingrese su opción: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Valor inválido
        }
    }

    public static int displayTargetCurrencyMenu(List<Currency> availableCurrencies, Currency sourceCurrency) {
        System.out.println("\n==============================================");
        System.out.println("     CONVERTIR " + sourceCurrency.getCode() + " A OTRA MONEDA      ");
        System.out.println("==============================================");
        System.out.println("Elija la moneda destino:");

        int index = 1;
        for (Currency currency : availableCurrencies) {
            if (!currency.getCode().equals(sourceCurrency.getCode())) {
                System.out.println(index + ". " + currency);
                index++;
            }
        }
        System.out.println("0. Volver al menú principal");
        System.out.print("Ingrese su opción: ");

        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Valor inválido
        }
    }

    public static double getAmountInput() {
        System.out.print("Ingrese la cantidad a convertir: ");
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1; // Valor inválido
        }
    }

    public static void displayConversionResult(Currency from, Currency to, double amount, double result, double rate) {
        System.out.println("\n==============================================");
        System.out.println("          RESULTADO DE LA CONVERSIÓN          ");
        System.out.println("==============================================");
        System.out.printf("%.2f %s = %.2f %s%n", amount, from.getCode(), result, to.getCode());
        System.out.println("Tasa de cambio: 1 " + from.getCode() + " = " + rate + " " + to.getCode());
        System.out.println("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }

    public static void displayGoodbyeMessage() {
        System.out.println("\n==============================================");
        System.out.println("    ¡Gracias por usar el Conversor de Monedas!    ");
        System.out.println("==============================================");
    }

    public static void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}
