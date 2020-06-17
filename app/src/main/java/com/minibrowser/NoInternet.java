package com.minibrowser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NoInternet extends AppCompatActivity implements View.OnClickListener {

    Button wifi, retry, exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);

        wifi = findViewById(R.id.button50);
        retry = findViewById(R.id.button51);
        exit = findViewById(R.id.button52);

        wifi.setOnClickListener(this);
        retry.setOnClickListener(this);
        exit.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(wifi == v) {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        if(retry == v) {
            CheckConnection checkConnection = new CheckConnection();
            if(checkConnection.isNetworkAvailable(this)) {
                startActivity(new Intent(NoInternet.this, MainActivity.class));
            }
            else {
                Toast.makeText(this, "", Toast.LENGTH_SHORT);
            }
        }
        if(exit == v) {
            finish();
        }
    }
}
