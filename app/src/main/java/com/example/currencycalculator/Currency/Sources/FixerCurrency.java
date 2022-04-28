package com.example.currencycalculator.Currency.Sources;

import android.content.Context;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.currencycalculator.Currency.pogos.Rate;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class FixerCurrency {

    RequestQueue requestQueue;

    public FixerCurrency() {
    }

    public List<Rate> getFixerData() throws JSONException {
        return convertToRates(requestJson());
    }

    private void startRequestQueue() {
        // Instantiate the cache
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024); // 1MB cap

        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());

        // Instantiate the RequestQueue with the cache and network.
        requestQueue = new RequestQueue(cache, network);

        // Start the queue
        requestQueue.start();
    }

    private JSONObject requestJson() {

        String url = "https://data.fixer.io/api/latest  ? access_key = API_KEY";
        final JSONObject[] jsonObject = {null};

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest

                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        jsonObject[0] = response;
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });
        startRequestQueue();
        requestQueue.add(jsonObjectRequest);

        return jsonObject[0];
    }

    private static List<Rate> convertToRates(JSONObject jsonObject) throws JSONException {

        List<Rate> rates = (List<Rate>) jsonObject.getJSONArray("rates");
        return rates;
    }

}
