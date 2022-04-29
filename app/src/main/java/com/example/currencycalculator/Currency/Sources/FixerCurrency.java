package com.example.currencycalculator.Currency.Sources;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.currencycalculator.Currency.Abstractions.CurrencyDAO;
import com.example.currencycalculator.Currency.pogos.Rate;
import com.example.currencycalculator.MainActivity;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/** Class for handling API Data */
public class FixerCurrency implements CurrencyDAO {

    public FixerCurrency() {
    }

    /** Method for starting the data gathering process */
    public void getRates(Context context) {
        requestJson(context);
    }

    /** Method for handling request and response from API */
    private void requestJson(Context context) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "insert api URL here";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            convertToRates(response);
                        } catch (JSONException exception) {
                            exception.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        System.out.println("Request error: " + error.getMessage());
                    }
                });

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    /** converts a JSONObject to a Rate object */
    private static void convertToRates(JSONObject jsonObject) throws JSONException {
        List<Rate> rates = new ArrayList<>();

        try {
            JSONArray jsonArray = jsonObject.getJSONArray("rates");

            for (int i=0; i < jsonArray.length(); i++) {
                Rate newRate = new Rate(jsonArray.getString(0), jsonArray.getDouble(1));
                rates.add(newRate);
            }

        } catch (JSONException exception) {
            System.out.println("Conversion error: " + exception.getMessage());
        } catch (NullPointerException nullPointerException) {
            System.out.println("Conversion Error: " + nullPointerException.getMessage());
        }

        MainActivity.showResult(rates);
    }

}
