# Conversor de Monedas - Challenge

#### Conversor de monedas realizado en Java como desafío para el programa ONE - Oracle Next Education.
#### Permite realizar conversiones entre divisas usando tipos de cambios actualizados mediante la API externa ExchangeRate.

### Características principales
    • Interfaz de consola interactiva y amigable
    • Tipos de cambio actualizados en tiempo real
    • Soporte para múltiples monedas (ARS, BOB, BRL, CLP, COP, USD)
    • Visualización detallada de tasas de cambio
    • Manejo robusto de errores
    • Diseño basado en principios SOLID y patrones MVC

### Componentes Principales
    1 - Main.java
        Es el punto de entrada de la aplicación, inicializa los controladores y maneja los errores globales.
    2 - Controllers
        • ConsoleController.java: Maneja la interacción del usuario.
        • CurrencyController.java: Coordina la lógica de conversión y actualización de las tasas.
    3 - Models
        • Currency.java: representa una moneda con código, nombre y tasa de cambio.
    4 - Service
        • CurrencyConverterService.java: realiza los cálculos de conversión.
        • ExchangeRateService.java: obtiene las tasas de cambio de la API externa.
    5 - Utilidades
        • ConsoleUtil.java: proporciona métodos para mostrar mensajes y menús.

### Requisitos
    • Java JDK 11 o superior.
    • Conexión a internet (para realizar la consulta a la API).
    • Dependencias:
          - Gson
          - HttpClient

### API utilizada
El programa utiliza la API de ExchangeRate-API con las siguientes características:

    • Llamadas asíncronas para mejor rendimiento.
    • Timeout de 10 segundos para las solicitudes.
    • Manejo de errores robusto.

  #### Monedas soportadas
    • ARS: peso argentino
    • BOB: boliviano de bolivia
    • BRL: real brasileño
    • CLP: peso chileno
    • COP: peso colombiano
    • USD: dólar estadounidense

#### Para agregar más monedas
##### 1. Modificar el método `parseExchangeRates`

##### 2. Agregar nuevas llamadas a addCurrency con el código y nombre de la nueva moneda.

### Contribuciones
Las contribuciones son bienvenidas. Por favor abre un issue o envía un pull request con tus sugerencias.
