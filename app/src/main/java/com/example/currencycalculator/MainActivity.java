package com.example.currencycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    private String[] items;
    ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Spinner dropdownMenu = findViewById(R.id.dropdownMenu);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);

        EditText inputText = findViewById(R.id.inputField);

        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setOnClickListener(v -> onClickSubmit(v));
    }

    private void onClickSubmit(View view) {

    }
}