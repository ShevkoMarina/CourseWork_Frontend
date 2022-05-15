package com.example.coursework2.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.coursework2.R;
import com.example.coursework2.model.SearchSettings;

import android.widget.CheckBox;


import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private CheckBox yandexCheckBox;
    private CheckBox googleCheckBox;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        yandexCheckBox = (CheckBox) findViewById(R.id.yandexCheckBox);
        googleCheckBox = (CheckBox) findViewById(R.id.googleCheckBox);

        sharedPreferences =  getSharedPreferences("UserData", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        SearchSettings searchSettings = getSearchSettings();
        editor.putString("searchSettings", searchSettings.name());
        editor.apply();

    }

    private SearchSettings getSearchSettings()
    {
        boolean yandexChecked = yandexCheckBox.isChecked();
        boolean googleChecked = googleCheckBox.isChecked();

        if (yandexChecked && googleChecked)
            return SearchSettings.Both;

        if (yandexChecked)
            return SearchSettings.Yandex;

        return SearchSettings.Google;
    }
}