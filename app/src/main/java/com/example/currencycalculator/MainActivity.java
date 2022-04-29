package com.example.currencycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.currencycalculator.Currency.CurrencyCalculator;
import com.example.currencycalculator.Currency.Sources.FixerCurrency;
import com.example.currencycalculator.Currency.Sources.MockCurrency;
import com.example.currencycalculator.Currency.pogos.Rate;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String[] currencyCodes = {"AUD", "CAD", "CHF", "CNY", "GBP", "JPY", "USD"};
    private static List<String> history = new ArrayList<>();
    private static String chosenCountry;
    private static Rate chosenRate;
    public static ArrayAdapter<String> dropDownAdapter = null;
    public static ArrayAdapter<String> listViewAdapter = null;

    private static Spinner dropdownMenu;
    private static ListView listView;
    private static EditText inputField;
    private static Button submitButton;

    /*MockCurrency MockCurrency = new MockCurrency();*/
    FixerCurrency FixerCurrency = new FixerCurrency();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup adapters
        dropDownAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, currencyCodes);
        listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, history);

        //Setup components
        dropdownMenu = findViewById(R.id.dropdownMenu);
        dropdownMenu.setAdapter(dropDownAdapter);
        listView = findViewById(R.id.listView);
        listView.setAdapter(listViewAdapter);
        listView.setTranscriptMode(2);
        inputField = findViewById(R.id.inputField);
        inputField.setOnClickListener(v -> onFieldClick());
        submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> {
            try {
                onClickSubmit();
            } catch (JSONException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Starts the currency calculation process
     */
    private void onClickSubmit() throws JSONException, InterruptedException {
        chosenCountry = dropdownMenu.getSelectedItem().toString();

        FixerCurrency.getRates(this);
    }

    /**
     * Removes start text in inputfield
     */
    private void onFieldClick() {
        EditText inputField = findViewById(R.id.inputField);
        if (String.valueOf(inputField.getText()).equals("Euro")) {
            inputField.setText("");
        }
    }

    /**
     * Adds processed Rates to visible listview
     */
    public static void showResult(List<Rate> rates) {
        double inputDouble = 0;
        inputDouble = Double.parseDouble(String.valueOf(inputField.getText()));

        for (Rate item : rates) {
            //Cleanup both strings
            String rateName = item.name.replace('"', Character.MIN_VALUE).trim();
            String chosenName = chosenCountry.replace('"', Character.MIN_VALUE).trim();

            //Ascertain equal
            boolean equal = rateName.equals(chosenName);

            if (equal) {
                chosenRate = item;
                break;
            }
        }

        //Run calculation and then show in listview
        if (chosenRate != null) {
            double result = CurrencyCalculator.calculateCurrency(inputDouble, chosenRate);
            history.add(chosenCountry + ": " + result);
            listViewAdapter.notifyDataSetChanged();
        } else {
            System.out.println("Rate was null.");
        }

    }
}