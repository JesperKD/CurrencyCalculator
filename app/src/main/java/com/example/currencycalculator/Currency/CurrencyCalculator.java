package com.example.currencycalculator.Currency;

import com.example.currencycalculator.Currency.pogos.Rate;

/**Handles all currency calculation*/
public class CurrencyCalculator {

    public CurrencyCalculator() {}

    /**Calculates the difference in currency dependant on a specific rate*/
    public static double calculateCurrency(double input, Rate rate) {
        double result = input * rate.spotRate;
        return result;
    }

}
