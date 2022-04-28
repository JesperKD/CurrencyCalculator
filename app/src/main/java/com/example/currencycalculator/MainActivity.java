package com.example.currencycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.currencycalculator.Currency.CurrencyCalculator;
import com.example.currencycalculator.Currency.Sources.MockCurrency;
import com.example.currencycalculator.Currency.pogos.Rate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String[] currencyCodes = {"AUD", "CAD", "CHF", "CNY", "GBP", "JPY", "USD"};
    private static List<String> history = new ArrayList<>();
    public static ArrayAdapter<String> dropDownAdapter = null;
    public static ArrayAdapter<String> listViewAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dropDownAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currencyCodes);
        listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, history);

        Spinner dropdownMenu = findViewById(R.id.dropdownMenu);
        dropdownMenu.setAdapter(dropDownAdapter);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(listViewAdapter);
        listView.setTranscriptMode(2);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> onClickSubmit());
    }

    private void onClickSubmit() {
        Spinner dropdownMenu = findViewById(R.id.dropdownMenu);
        EditText inputText = findViewById(R.id.inputField);

        double inputDouble = Double.parseDouble(String.valueOf(inputText.getText()));

        String chosenCountry = dropdownMenu.getSelectedItem().toString();
        Rate chosenRate = null;

        for (Rate item : MockCurrency.getMockData()) {
            if (item.name.equals(chosenCountry)) {
                chosenRate = item;
            }
        }

        double result = CurrencyCalculator.calculateCurrency(inputDouble, chosenRate);

        history.add(chosenCountry + ": " + result);
        listViewAdapter.notifyDataSetChanged();
    }
}