package com.example.currencycalculator.Currency.Abstractions;

import com.example.currencycalculator.Currency.pogos.Rate;

import java.util.List;

public interface CurrencyDAO {
    public List<Rate> getRates(String base);
}
