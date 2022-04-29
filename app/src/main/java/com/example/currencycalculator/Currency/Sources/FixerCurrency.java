package com.example.currencycalculator.Currency.Sources;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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

/**
 * Class for handling API Data
 */
public class FixerCurrency implements CurrencyDAO {

    public FixerCurrency() {
    }

    /**
     * Method for starting the data gathering process
     */
    public void getRates(Context context) {
        requestJson(context);
    }

    /**
     * Method for handling request and response from API
     */
    private void requestJson(Context context) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://v1.nocodeapi.com/johnnybravo/cx/sDGHcWXqNXLbFXmE/rates";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
        queue.add(stringRequest);
    }

    /**
     * converts a JSONObject to a Rate object
     */
    private static void convertToRates(String response) throws JSONException {
        List<Rate> rates = new ArrayList<>();

        String[] roughSort = response.split("\\{");
        String[] mediumSort = roughSort[2].split(",");

        for (String item : mediumSort
        ) {
            item.replace('}', Character.MIN_VALUE);
        }

        try {
            for (String item : mediumSort
            ) {
                String[] fineSort = item.split(":");
                Rate newRate = new Rate(
                        fineSort[0].replace('}', Character.MIN_VALUE),
                        Double.parseDouble(fineSort[1].replace('}', Character.MIN_VALUE))
                );
                rates.add(newRate);
            }
        } catch (
                Exception exception) {
            System.out.println("Conversion Error: " + exception.getMessage());
        }


        MainActivity.showResult(rates);
    }
}