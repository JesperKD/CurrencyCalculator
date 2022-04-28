package com.example.currencycalculator.Currency.Sources;

import com.example.currencycalculator.Currency.pogos.Rate;

public class MockCurrency {

    public MockCurrency() {
    }

    public static Rate[] getMockData() {

        Rate[] array = {
                new Rate("AUD", 1.566015),
                new Rate("CAD", 1.560132),
                new Rate("CHF", 1.154727),
                new Rate("CNY", 7.827874),
                new Rate("GBP", 0.882047),
                new Rate("JPY", 132.360679),
                new Rate("USD", 1.23396)
        };

        return array;
    }


}
