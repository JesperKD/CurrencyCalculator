package com.example.currencycalculator.Currency.Abstractions;

import android.content.Context;

import org.json.JSONException;

public interface CurrencyDAO {
    public void getRates(Context context) throws JSONException;
}
