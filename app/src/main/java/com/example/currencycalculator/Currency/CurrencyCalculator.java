package com.example.currencycalculator.Currency;

import com.example.currencycalculator.Currency.pogos.Rate;

public class CurrencyCalculator {

    public CurrencyCalculator() {}

    public static double calculateCurrency(double input, Rate rate) {
        double result = input * rate.spotRate;
        return result;
    }

}
