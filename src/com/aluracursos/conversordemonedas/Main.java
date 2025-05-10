package com.aluracursos.conversordemonedas;

import com.aluracursos.conversordemonedas.controllers.ConsoleController;
import com.aluracursos.conversordemonedas.controllers.CurrencyController;

public class Main {
    public static void main(String[] args) {
        try {
            CurrencyController currencyController = new CurrencyController();

            ConsoleController consoleController = new ConsoleController(
                    currencyController,
                    currencyController.getConverterService()
            );

            consoleController.start();
        } catch (Exception e) {
            System.err.println("Error fatal en la aplicación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}